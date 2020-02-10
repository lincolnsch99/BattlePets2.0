/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Commenter: Lincoln Schroeder
 *
 * Purpose: This class contains basic logic for all AiInstances, and also keeps track of all of the current players
 * in the fight.
 */

package edu.dselent.CustomAi;

import edu.dselent.event.*;
import edu.dselent.player.PetInstance;
import edu.dselent.player.PetTypes;
import edu.dselent.settings.PlayerInfo;
import edu.dselent.skill.Skills;


import java.security.SecureRandom;
import java.util.*;

public abstract class AiInstance extends PetInstance
{
    private Map<PlayerInfoHolder, Float> predictableWeights;
    private Map<PlayerInfoHolder, Float> rebelliousWeights;
    private Map<PlayerInfoHolder, Skills> abilityPredictions;
    private Map<PlayerInfoHolder, Skills> abilitiesChosen;
    private Map<PlayerInfoHolder, List<Skills>> pastAbilitiesChosen;
    private List<PlayerInfoHolder> playerInfos;
    private int recursiveCount, currentRound;

    public AiInstance(int playableUid, PlayerInfo playerInfo)
    {
        super(playableUid, playerInfo);
        predictableWeights = new HashMap<>();
        rebelliousWeights = new HashMap<>();
        abilityPredictions = new HashMap<>();
        abilitiesChosen = new HashMap<>();
        playerInfos = new ArrayList<>();
        pastAbilitiesChosen = new HashMap<>();
        recursiveCount = 0;
    }

    /**
     * Initializes all the info maps to empty.
     */
    private void initializeMaps()
    {
        predictableWeights = new HashMap<>();
        for (PlayerInfoHolder playerInfo : playerInfos)
        {
            predictableWeights.put(playerInfo, 1f);
        }

        rebelliousWeights = new HashMap<>();
        for (PlayerInfoHolder playerInfo : playerInfos)
        {
            rebelliousWeights.put(playerInfo, 1.5f);
        }

        abilitiesChosen = new HashMap<>();
        for(PlayerInfoHolder playerInfo : playerInfos)
        {
            abilitiesChosen.put(playerInfo, null);
        }

        abilityPredictions = new HashMap<>();
        for(PlayerInfoHolder playerInfo : playerInfos)
        {
            abilityPredictions.put(playerInfo, null);
        }

        pastAbilitiesChosen = new HashMap<>();
        for(PlayerInfoHolder playerInfo : playerInfos)
        {
            pastAbilitiesChosen.put(playerInfo, new ArrayList<>());
        }
    }

    /**
     * Finds the best possible skill choice for any power pet.
     * @param playerInfo the pet to test.
     * @return the pet's best skill choice.
     */
    public Skills bestSkillChoicePower(PlayerInfoHolder playerInfo)
    {
        int victimIndex = getIndexOfVictim(playerInfo);
        PlayerInfoHolder victim = playerInfos.get(victimIndex);
        Skills victimBestSkillChoice = null, attackerBestSkillChoice = null;

        if (recursiveCount < playerInfos.size())
        {
            recursiveCount++;
            switch (victim.getPetType())
            {
                case POWER:
                    victimBestSkillChoice = bestSkillChoicePower(victim);
                    break;
                case SPEED:
                    victimBestSkillChoice = bestSkillChoiceSpeed(victim);
                    break;
                case INTELLIGENCE:
                    victimBestSkillChoice = bestSkillChoiceIntelligence(victim);
                    break;
            }
        }

        if (victimBestSkillChoice == null)
        {
            if(victim.getPetType() == PetTypes.POWER)
            {
                if (playerInfo.getSkillRechargeTime(Skills.ROCK_THROW) != 0
                        && victim.getSkillRechargeTime(Skills.ROCK_THROW) != 0)
                {
                    attackerBestSkillChoice = Skills.SCISSORS_POKE;
                } else if (playerInfo.getSkillRechargeTime(Skills.SCISSORS_POKE) != 0
                        && victim.getSkillRechargeTime(Skills.ROCK_THROW) != 0)
                {
                    attackerBestSkillChoice = Skills.ROCK_THROW;
                } else if (playerInfo.getSkillRechargeTime(Skills.PAPER_CUT) != 0
                        && victim.getSkillRechargeTime(Skills.ROCK_THROW) != 0)
                {
                    attackerBestSkillChoice = Skills.SCISSORS_POKE;
                } else if (playerInfo.getSkillRechargeTime(Skills.ROCK_THROW) != 0
                        && victim.getSkillRechargeTime(Skills.SCISSORS_POKE) != 0)
                {
                    attackerBestSkillChoice = Skills.PAPER_CUT;
                } else if (playerInfo.getSkillRechargeTime(Skills.SCISSORS_POKE) != 0
                        && victim.getSkillRechargeTime(Skills.SCISSORS_POKE) != 0)
                {
                    attackerBestSkillChoice = Skills.PAPER_CUT;
                } else if (playerInfo.getSkillRechargeTime(Skills.PAPER_CUT) != 0
                        && victim.getSkillRechargeTime(Skills.SCISSORS_POKE) != 0)
                {
                    attackerBestSkillChoice = Skills.SCISSORS_POKE;
                } else if (playerInfo.getSkillRechargeTime(Skills.ROCK_THROW) != 0
                        && victim.getSkillRechargeTime(Skills.PAPER_CUT) != 0)
                {
                    attackerBestSkillChoice = Skills.PAPER_CUT;
                } else if (playerInfo.getSkillRechargeTime(Skills.SCISSORS_POKE) != 0
                        && victim.getSkillRechargeTime(Skills.PAPER_CUT) != 0)
                {
                    attackerBestSkillChoice = Skills.ROCK_THROW;
                } else if (playerInfo.getSkillRechargeTime(Skills.PAPER_CUT) != 0
                        && victim.getSkillRechargeTime(Skills.PAPER_CUT) != 0)
                {
                    attackerBestSkillChoice = Skills.ROCK_THROW;
                }
                else {
                    int randomNumber = new SecureRandom().nextInt(3);
                    if (randomNumber == 0)
                        attackerBestSkillChoice = Skills.ROCK_THROW;
                    else if (randomNumber == 1)
                        attackerBestSkillChoice = Skills.SCISSORS_POKE;
                    else if (randomNumber == 2)
                        attackerBestSkillChoice = Skills.PAPER_CUT;
                }
            }
        }
        else
        {
            switch (victimBestSkillChoice)
            {
                case ROCK_THROW:
                    if (playerInfo.getSkillRechargeTime(Skills.PAPER_CUT) < 1)
                        attackerBestSkillChoice = Skills.PAPER_CUT;
                    else
                        attackerBestSkillChoice = Skills.ROCK_THROW;
                    break;
                case SCISSORS_POKE:
                    if (playerInfo.getSkillRechargeTime(Skills.ROCK_THROW) < 1)
                        attackerBestSkillChoice = Skills.ROCK_THROW;
                    else
                        attackerBestSkillChoice = Skills.SCISSORS_POKE;
                    break;
                case PAPER_CUT:
                    if (playerInfo.getSkillRechargeTime(Skills.SCISSORS_POKE) < 1)
                        attackerBestSkillChoice = Skills.SCISSORS_POKE;
                    else
                        attackerBestSkillChoice = Skills.PAPER_CUT;
                    break;
            }

        }
        recursiveCount = 0;
        return attackerBestSkillChoice;
    }

    /**
     * Finds the best possible skill choice for any speed pet.
     * @param playerInfo the pet to test.
     * @return the pet's best skill choice.
     */
    public Skills bestSkillChoiceSpeed(PlayerInfoHolder playerInfo)
    {
        int victimIndex = getIndexOfVictim(playerInfo);
        PlayerInfoHolder victim = playerInfos.get(victimIndex);
        Skills attackerBestSkillChoice = null;

        double victimHpPercent = victim.calculateHpPercent();
        if (victimHpPercent >= 0.75 && playerInfo.getSkillRechargeTime(Skills.ROCK_THROW) < 1)
        {
            attackerBestSkillChoice = Skills.ROCK_THROW;
        }
        else if (victimHpPercent >= 0.25 && playerInfo.getSkillRechargeTime(Skills.SCISSORS_POKE) < 1)
        {
            attackerBestSkillChoice = Skills.SCISSORS_POKE;
        }
        else if (playerInfo.getSkillRechargeTime(Skills.PAPER_CUT) < 1
                && playerInfo.getSkillRechargeTime(Skills.PAPER_CUT) < 1)
        {
            attackerBestSkillChoice = Skills.PAPER_CUT;
        }
        else
        {
            if (playerInfo.getSkillRechargeTime(Skills.ROCK_THROW) < 1)
            {
                attackerBestSkillChoice = Skills.ROCK_THROW;
            }
            else if (playerInfo.getSkillRechargeTime(Skills.PAPER_CUT) < 1)
            {
                attackerBestSkillChoice = Skills.PAPER_CUT;
            }
            else if (playerInfo.getSkillRechargeTime(Skills.SCISSORS_POKE) < 1)
            {
                attackerBestSkillChoice = Skills.SCISSORS_POKE;
            }
        }

        recursiveCount = 0;
        return attackerBestSkillChoice;
    }

    /**
     * Finds the best possible skill choice for any intelligence pet.
     * @param playerInfo the pet to test.
     * @return the pet's best skill choice.
     */
    public Skills bestSkillChoiceIntelligence(PlayerInfoHolder playerInfo)
    {
        int victimIndex = getIndexOfVictim(playerInfo);
        PlayerInfoHolder victim = playerInfos.get(victimIndex);
        Skills attackerBestSkillChoice = null;

        if (victim.getSkillRechargeTime(Skills.ROCK_THROW) > 0)
        {
            if (playerInfo.getSkillRechargeTime(Skills.PAPER_CUT) < 1)
                attackerBestSkillChoice = Skills.PAPER_CUT;
            else
                attackerBestSkillChoice = Skills.ROCK_THROW;
        }
        else if (victim.getSkillRechargeTime(Skills.PAPER_CUT) > 0)
        {
            if (playerInfo.getSkillRechargeTime(Skills.SCISSORS_POKE) < 1)
                attackerBestSkillChoice = Skills.SCISSORS_POKE;
            else
                attackerBestSkillChoice = Skills.PAPER_CUT;
        }
        else if (victim.getSkillRechargeTime(Skills.SCISSORS_POKE) > 0)
        {
            if (playerInfo.getSkillRechargeTime(Skills.ROCK_THROW) < 1)
                attackerBestSkillChoice = Skills.ROCK_THROW;
            else
                attackerBestSkillChoice = Skills.SCISSORS_POKE;
        }
        else
        {
            if (playerInfo.getSkillRechargeTime(Skills.ROCK_THROW) < 1)
                attackerBestSkillChoice = Skills.ROCK_THROW;
            else if (playerInfo.getSkillRechargeTime(Skills.PAPER_CUT) < 1)
                attackerBestSkillChoice = Skills.PAPER_CUT;
            else if (playerInfo.getSkillRechargeTime(Skills.SCISSORS_POKE) < 1)
                attackerBestSkillChoice = Skills.SCISSORS_POKE;
        }
        return attackerBestSkillChoice;
    }

    /**
     * Gets the index of the victim for the given player.
     * @param attackingPlayer player who is attacking the victim.
     * @return index of the victim.
     */
    public int getIndexOfVictim(PlayerInfoHolder attackingPlayer)
    {
        int attackingPlayerIndex = playerInfos.indexOf(attackingPlayer);
        int victimIndex = (attackingPlayerIndex + 1) % playerInfos.size();
        if (playerInfos.get(victimIndex).getCurrentHp() <= 0)
        {
            return getIndexOfVictim(playerInfos.get(victimIndex));
        }
        else
        {
            return victimIndex;
        }
    }

    /**
     * Gets the index of this player's attacker.
     * @param me ...me
     * @return index of the player's attacker.
     */
    public int getIndexOfMyAttacker(PlayerInfoHolder me)
    {
        int myIndex = playerInfos.indexOf(me);
        int attackerIndex = (myIndex + playerInfos.size() - 1) % playerInfos.size();
        if (playerInfos.get(attackerIndex).getCurrentHp() <= 0)
        {
            return getIndexOfVictim(playerInfos.get(attackerIndex));
        }
        else
        {
            return attackerIndex;
        }
    }


    /**
     * Updates the list of past abilities for the given player.
     * @param playerInfo the player we're updating.
     * @param chosenSkill the skill they chose.
     */
    private void updatePastAbilitiesChosen(PlayerInfoHolder playerInfo, Skills chosenSkill)
    {
        List<Skills> currentPastSkills = pastAbilitiesChosen.get(playerInfo);
        if(currentPastSkills != null)
        {
            currentPastSkills.add(chosenSkill);
            pastAbilitiesChosen.put(playerInfo, currentPastSkills);
        }
        else
        {
            currentPastSkills = new ArrayList<>();
            currentPastSkills.add(chosenSkill);
            pastAbilitiesChosen.put(playerInfo, currentPastSkills);
        }
    }

    /**
     * Whenever the event bus sends out an event, we update all our player information and create our predictions for
     * each round.
     * @param event the event being broadcasted.
     */
    @Override
    public void update(Object event)
    {
        // When fight starts, we need to erase all our previous information.
        if (event instanceof FightStartEvent)
        {
            playerInfos = new ArrayList<>();
            for (PlayerEventInfo player : ((FightStartEvent) event).getPlayerEventInfoList())
            {
                PlayerInfoHolder playerInfo = new PlayerInfoHolder(player.getPlayableUid());
                playerInfo.setStartingHp(player.getStartingHp());
                playerInfo.setPetName(player.getPetName());
                playerInfo.setPetType(player.getPetType());
                playerInfo.setPlayerType(player.getPlayerType());
                playerInfos.add(playerInfo);
            }
            initializeMaps();
        }
        else if (event instanceof RoundStartEvent)
        {
            // For each round, we need to generate our ability predictions and update our weights on whether or not
            // the pets are acting predictably.
            for (PlayerInfoHolder playerInfo : playerInfos)
            {
                currentRound = ((RoundStartEvent) event).getRoundNumber();
                if (((RoundStartEvent) event).getRoundNumber() > 2)
                {
                    if (abilitiesChosen.get(playerInfo) == abilityPredictions.get(playerInfo))
                    {
                        predictableWeights.put(playerInfo, predictableWeights.get(playerInfo) + 0.25f);
                    }
                    else
                    {
                        rebelliousWeights.put(playerInfo, rebelliousWeights.get(playerInfo) + 0.25f);
                    }
                }
            }
            abilityPredictions = new HashMap<>();
            abilitiesChosen = new HashMap<>();
            for (PlayerInfoHolder playerInfo : playerInfos)
            {
                if (!playerInfo.isAsleep())
                {
                    playerInfo.updateCumulativeRd();
                    playerInfo.decrementRechargeTimes();
                    switch (playerInfo.getPetType())
                    {
                        case POWER:
                            abilityPredictions.put(playerInfo, bestSkillChoicePower(playerInfo));
                            break;
                        case SPEED:
                            abilityPredictions.put(playerInfo, bestSkillChoiceSpeed(playerInfo));
                            break;
                        case INTELLIGENCE:
                            abilityPredictions.put(playerInfo, bestSkillChoiceIntelligence(playerInfo));
                            break;
                    }
                }
            }
        }
        else if (event instanceof AttackEvent)
        {
            // Update HP, cool-downs, cumulativeRD, and whatnot.
            if (event instanceof AttackEventShootTheMoon)
            {
                for (PlayerInfoHolder playerInfo : playerInfos)
                {
                    if (((AttackEvent) event).getAttackingPlayableUid() == playerInfo.getPlayableUid())
                    {
                        playerInfo.setRechargeTime(Skills.SHOOT_THE_MOON, 7);
                        playerInfo.setLastRandomDamageDealt(((AttackEvent) event).getDamage().getRandomDamage());
                        abilitiesChosen.put(playerInfo, Skills.SHOOT_THE_MOON);
                        updatePastAbilitiesChosen(playerInfo, Skills.SHOOT_THE_MOON);
                    }
                    else if (((AttackEvent) event).getVictimPlayableUid() == playerInfo.getPlayableUid())
                    {
                        playerInfo.updateHp(((AttackEvent) event).getDamage().calculateTotalDamage());
                        playerInfo.setLastRandomDamageTaken(((AttackEvent) event).getDamage().getRandomDamage());
                    }
                }
            }
            else
            {
                for (PlayerInfoHolder playerInfo : playerInfos)
                {
                    if (((AttackEvent) event).getAttackingPlayableUid() == playerInfo.getPlayableUid())
                    {
                        int rechargeTime = 2;
                        if (((AttackEvent) event).getAttackingSkillChoice() == Skills.REVERSAL_OF_FORTUNE)
                        {
                            rechargeTime = 7;
                        }
                        playerInfo.setRechargeTime(((AttackEvent) event).getAttackingSkillChoice(), rechargeTime);
                        playerInfo.setLastRandomDamageDealt(((AttackEvent) event).getDamage().getRandomDamage());
                        abilitiesChosen.put(playerInfo, ((AttackEvent) event).getAttackingSkillChoice());
                        updatePastAbilitiesChosen(playerInfo, ((AttackEvent) event).getAttackingSkillChoice());
                    }
                    else if (((AttackEvent) event).getVictimPlayableUid() == playerInfo.getPlayableUid())
                    {
                        playerInfo.updateHp(((AttackEvent) event).getDamage().calculateTotalDamage());
                        playerInfo.setLastRandomDamageTaken(((AttackEvent) event).getDamage().getRandomDamage());
                    }
                }
            }
        }
    }

    /**
     * Thoroughly checks through the given player's past ability choices and tries to find a repeating pattern. Checks
     * for patterns as big as 1/2 of the player's total past choices. (Ex: If it's round 50, this will be able to find
     * a pattern up to 24 in length)
     * @param playerCheck the player being checked for patterns/predictability.
     * @return an empty list if there is no pattern, else return the detected pattern.
     */
    public List<Skills> checkForPattern(PlayerInfoHolder playerCheck)
    {
        List<Skills> playersPastChoices = pastAbilitiesChosen.get(playerCheck);
        if(playersPastChoices == null)
            return null;
        if(playersPastChoices.size() > 4)
        {
            for(int k = 0; k < playersPastChoices.size() / 2; k++)
            {
                for (int i = 2; i <= (playersPastChoices.size() - k) / 2; i++)
                {
                    List<Skills> testPattern = new ArrayList<>();
                    for (int j = 0; j < i; j++)
                    {
                        testPattern.add(playersPastChoices.get(j + k));
                    }

                    boolean hasPattern = true;
                    int checkIndex = k, testPatternIndex = 0;
                    while (hasPattern && checkIndex < playersPastChoices.size())
                    {
                        if (playersPastChoices.get(checkIndex) != testPattern.get(testPatternIndex))
                            hasPattern = false;
                        else
                        {
                            checkIndex++;
                            testPatternIndex = (testPatternIndex + 1) % testPattern.size();
                        }
                    }
                    if (hasPattern)
                        return testPattern;
                }
            }
        }
        return null;
    }

    /**
     * Getter for the list of players in the fight.
     * @return list of players.
     */
    public List<PlayerInfoHolder> getPlayerInfos()
    {
        return playerInfos;
    }

    /**
     * Getter for the predictions for this round.
     * @return map of players->predictions.
     */
    public Map<PlayerInfoHolder, Skills> getAbilityPredictions()
    {
        return abilityPredictions;
    }

    /**
     * Getter for the past skill choices of all players.
     * @return map of player->list of skills.
     */
    public Map<PlayerInfoHolder, List<Skills>> getPastAbilitiesChosen()
    {
        return pastAbilitiesChosen;
    }

    /**
     * Getter for the current round.
     * @return int representing the current round.
     */
    public int getCurrentRound()
    {
        return currentRound;
    }
}