/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Commenter: Lincoln Schroeder
 *
 * Purpose: A class to handle all logic for completing a single round of a fight. Uses the Round class to store and
 * update necessary data.
 */

package TeamVierAugen.Controllers;

import TeamVierAugen.*;
import TeamVierAugen.GameSegments.Round;
import TeamVierAugen.Playables.AIPlayerInstance;
import TeamVierAugen.Playables.Playable;
import TeamVierAugen.Playables.PlayerInstance;
import TeamVierAugen.Skills.Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundController {

    private static final int SKILL_RECHARGE_TIME_1 = 1;
    private static final int SKILL_RECHARGE_TIME_2 = 6;

    private InputOutputController ioController;
    private Round round;

    /**
     * This default constructor instantiates the IO Controller
     * and the Round object.
     */
    public RoundController(EventBusSubject eventBusSubject, int roundNumber)
    {
        ioController = new InputOutputController();
        round = new Round(eventBusSubject);
        round.setRoundNumber(roundNumber);
    }

    /**
     * This method returns the Round object that belongs to the class.
     *
     * @return round object stored in this controller
     */
    public Round getRound() {
        return round;
    }

    /**
     * Goes through a single 'round' of a fight. Asks for players' skill choices, then calculates damage.
     * @return an updated version of the players, with correct health and skill recharges.
     */
    public List<Playable> completeOneRound() {
        RoundStartEvent event = new RoundStartEvent(round.getRoundNumber());
        round.getEventBusSubject().setEvent(event);
        List<Playable> playerStatuses = new ArrayList<>(round.getPlayerList());

        Map<Playable, Skills> playerSkillChoices = new HashMap<Playable, Skills>();

        for (int i = 0; i < playerStatuses.size(); i++) {
            Skills chosenSkill = playerStatuses.get(i).chooseSkill();
            playerSkillChoices.put(playerStatuses.get(i), chosenSkill);
            if (chosenSkill == Skills.SHOOT_THE_MOON) {
                round.addSkillPrediction(playerStatuses.get(i), playerStatuses.get(i).getSkillPrediction());
            }
        }

        try {
            List<Playable> playerList = round.getPlayerList();
            for (int i = 0; i < playerList.size(); i++) {
                    Damage damage = calculateDamage(playerList.get(i), playerList.get((i + 1) % playerList.size()),
                            playerSkillChoices, round.getSkillPredictions());
                    round.addPlayerDamage(playerList.get((i + 1) % playerList.size()), damage);

            }

            Map<Playable, Damage> playerDamages = round.getPlayerDamages();

            checkSleepScent(playerList);

            for (int i = 0; i < playerDamages.size(); i++) {
                double receivedDamage = playerDamages.get(playerList.get(i)).getRandomDamage();
                double dealtDamage;
                dealtDamage = playerDamages.get(playerList.get((i + 1) % playerList.size())).getRandomDamage();

                if (playerList.get(i) instanceof PlayerInstance)
                    ((PlayerInstance) playerList.get(i)).updateCumulativeRD(receivedDamage - dealtDamage);
                else if(playerList.get(i) instanceof  AIPlayerInstance)
                    ((AIPlayerInstance) playerList.get(i)).updateCumulativeRD(receivedDamage - dealtDamage);
            }
            for (int i = 0; i < playerStatuses.size(); i++)
                playerStatuses.get(i).decrementRechargeTimes();
            damagePlayers(playerList, round.getPlayerDamages(), playerSkillChoices);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        round.resetSkillPredictions();

        return playerStatuses;
    }


    /**
     * Iterates through the playables and updates their hp for the given values.
     * @param players the list of playables.
     * @param playerDamages the damage to be distributed.
     */
    private void damagePlayers(List<Playable> players, Map<Playable, Damage> playerDamages, Map<Playable, Skills> skillChoices)
    {
        for (int i = 0; i < players.size(); i++)
        {
            ioController.displayDamage(playerDamages.get(players.get(i)), players.get(i));
            int attackingPlayerIndex = (i - 1 + players.size()) % players.size();
            if(skillChoices.get(players.get(attackingPlayerIndex)) != Skills.SHOOT_THE_MOON)
            {
                AttackEvent event = new AttackEvent(players.get(attackingPlayerIndex).getPlayableId(), players.get(i).getPlayableId(),
                        skillChoices.get(players.get(attackingPlayerIndex)), playerDamages.get(players.get(i)));
                round.getEventBusSubject().setEvent(event);
            }
            else
            {
                AttackEventShootTheMoon event = new AttackEventShootTheMoon.AttackEventShootTheMoonBuilder()
                        .withAttackingPlayableUid(players.get(attackingPlayerIndex).getPlayableId())
                        .withAttackingSkillChoice(skillChoices.get(players.get(attackingPlayerIndex)))
                        .withDamage(playerDamages.get(players.get(i)))
                        .withpredictedSkillEnum(round.getSkillPredictions().get(players.get(attackingPlayerIndex)))
                        .withVictimPlayableUid(players.get(i).getPlayableId())
                        .build();
                round.getEventBusSubject().setEvent(event);
            }
        }
    }

    /**
     * Calculate the damage point and display the random/conditional damage using InputOutputController.
     * @param attackingPlayer is the player to be attacking
     * @param defendingPlayer is the player to be defending
     * @param skillsUsed      is the skill to be using for attack and defend
     * @return calculateTotalDamage
     */
    private Damage calculateDamage(Playable attackingPlayer, Playable defendingPlayer, Map<Playable,
            Skills> skillsUsed, Map<Playable, Skills> skillPredictions) throws Exception
    {
        Damage damageCalc = new Damage(0, 0);
        RandomNumberGenerator randomNumberGenerator = RandomNumberGenerator.getInstance();
        damageCalc.setRandomDamage(randomNumberGenerator.getNextRandom());
        if (skillsUsed.get(attackingPlayer) == Skills.SHOOT_THE_MOON)
        {
            damageCalc.calculateShootTheMoonConditional(skillPredictions.get(attackingPlayer),
                    skillsUsed.get(defendingPlayer));
        }
        else if (skillsUsed.get(attackingPlayer) == Skills.REVERSAL_OF_FORTUNE)
        {
            double cumulativeRD = 0.0;
            if (attackingPlayer instanceof PlayerInstance)
            {
                cumulativeRD = ((PlayerInstance) attackingPlayer).getCumulativeRD();
                ((PlayerInstance) attackingPlayer).updateCumulativeRD(-((PlayerInstance) attackingPlayer).getCumulativeRD());
            }
            damageCalc.setRandomDamage(damageCalc.getRandomDamage() + cumulativeRD);
            damageCalc.setConditionalDamage(cumulativeRD);

        }
        else
        {
            switch (attackingPlayer.getPetType())
            {
                case POWER:
                    damageCalc.calculatePowerConditional(skillsUsed.get(attackingPlayer),
                            skillsUsed.get(defendingPlayer));
                    break;
                case SPEED:
                    damageCalc.calculateSpeedConditional(skillsUsed.get(attackingPlayer),
                            skillsUsed.get(defendingPlayer),
                            defendingPlayer.calculateHpPercent());
                    break;
                case INTELLIGENCE:
                    damageCalc.calculateIntelligenceConditional(skillsUsed.get(attackingPlayer),
                            defendingPlayer);
                    break;
                default:
                    throw new Exception("Invalid pet type!");
            }
        }
        return damageCalc;
    }

    /**
     * Iterates through each of the playables, checks if they are going to fall asleep during this round, and applies
     * their Sleep Scent damage if so. It then does another iteration to check if other playables feel asleep due
     * to the first distribution of Sleep Scent damages. If more pets are falling asleep, more passives are applied. It
     * does this continuously until all pets left are in no danger of falling asleep.
     * @param playerList the current list of players.
     */
    private void checkSleepScent(List<Playable> playerList)
    {
        Map<Playable, Damage> playerDamages = round.getPlayerDamages();
        List<Playable> playablesAlreadyCalculated = new ArrayList<>();

        List<Playable> petsGoingToSleep = new ArrayList<>();

        for (int i = 0; i < playerList.size(); i++)
        {
            double receivedDamage = playerDamages.get(playerList.get(i)).calculateTotalDamage();
            if (playerList.get(i).getCurrentHp() - receivedDamage <= 0.0) {
                petsGoingToSleep.add(playerList.get(i));
            }
        }

        while (petsGoingToSleep.size() > 0)
        {
            // Go through the playerList, if they are going to sleep, add damage to the next player
            for (int i = 0; i < playerList.size(); i++)
            {
                if (petsGoingToSleep.contains(playerList.get(i)))

                {
                    PlayerInstance player;
                    AIPlayerInstance aiPlayer;
                    Playable temp = playerList.get(i);
                    if(temp instanceof PlayerInstance)
                    {
                        player = (PlayerInstance) playerList.get(i);
                        Damage newDamage = playerDamages.get(playerList.get((i + 1) % playerList.size()));
                        newDamage.setRandomDamage(newDamage.getRandomDamage() + player.getCumulativeRD());
                        player.updateCumulativeRD(-(player.getCumulativeRD()));
                        round.addPlayerDamage(playerList.get((i + 1) % playerList.size()), newDamage);
                    }
                    else if(temp instanceof AIPlayerInstance)
                    {
                        aiPlayer = (AIPlayerInstance) playerList.get(i);
                        Damage newDamage = playerDamages.get(playerList.get((i + 1) % playerList.size()));
                        newDamage.setRandomDamage(newDamage.getRandomDamage() + aiPlayer.getCumulativeRD());
                        aiPlayer.updateCumulativeRD(-(aiPlayer.getCumulativeRD()));
                        round.addPlayerDamage(playerList.get((i + 1) % playerList.size()), newDamage);
                    }


                }
            }

            // Dump playables into the already calculated list
            for(int i = 0; i < petsGoingToSleep.size(); i++)
                playablesAlreadyCalculated.add(petsGoingToSleep.get(i));

            // Grab any other playables that have now fallen asleep
            petsGoingToSleep.clear();
            for (int i = 0; i < playerList.size(); i++)
            {
                double receivedDamage = playerDamages.get(playerList.get(i)).calculateTotalDamage();
                if (playerList.get(i).getCurrentHp() - receivedDamage <= 0.0 &&
                        !playablesAlreadyCalculated.contains(playerList.get(i)))
                {
                    petsGoingToSleep.add(playerList.get(i));
                }
            }
        }
    }
}
