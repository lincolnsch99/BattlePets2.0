/**
 * Authors: Alex Ahlrichs, Ross Baldwin, Jared Hollenberger, Zexin Liu, Lincoln Schroeder
 *
 * Commenter: Lincoln Schroeder
 *
 * Purpose: This class contains the logic for our Intelligence AI. Extends from AiInstance, so it uses the current
 * fight information to decide which skills offer the most damage with least repercussions, and can detect patterns in
 * the victim's skill choice history.
 */

package edu.dselent.CustomAi;

import edu.dselent.settings.PlayerInfo;
import edu.dselent.skill.Skills;

import java.util.ArrayList;
import java.util.List;

public class IntelligenceAi extends AiInstance
{
    private Skills stmSkillPrediction;

    public IntelligenceAi(int playableUid, PlayerInfo playerInfo)
    {
        super(playableUid, playerInfo);
    }

    /**
     * Depending on which pet type is attacking, make different decisions.
     * @return our skill choice.
     */
    @Override
    public Skills chooseSkill()
    {
        int myIndex = myIndex();
        int attackerIndex = getIndexOfMyAttacker(getPlayerInfos().get(myIndex));
        PlayerInfoHolder attacker = getPlayerInfos().get(attackerIndex);

        switch(attacker.getPetType())
        {
            case POWER:
                return bestSkillChoiceAgainstPower(attacker);
            case SPEED:
                return bestSkillChoiceAgainstSpeed(attacker);
            case INTELLIGENCE:
                return bestSkillChoiceAgainstIntelligence(attacker);
            default:
                return getAbilityPredictions().get(getPlayerInfos().get(myIndex()));
        }
    }

    /**
     * Uses Reversal_Of_Fortune if the damage is good, otherwise tries to chase the power pet's previous ability choice.
     * We cannot be punished if the power pet's conditional damage is impossible. It also manually checks if there
     * have been any recent patterns detected. If so, use Shoot_The_Moon and make our prediction.
     * @param powerPlayer the power pet that is attacking us.
     * @return our skill choice.
     */
    private Skills bestSkillChoiceAgainstPower(PlayerInfoHolder powerPlayer)
    {
        Skills mySkillPrediction = getAbilityPredictions().get(getPlayerInfos().get(myIndex()));
        Skills myBestSkillChoice = null;
        if(getPlayerInfos().get(myIndex()).getCumulativeRdDifference() >= 5
                && getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) < 1) // If our cumulative random difference is high, go ahead and use it.
            myBestSkillChoice = Skills.REVERSAL_OF_FORTUNE;
        else
        {
            if (powerPlayer.getSkillRechargeTime(Skills.ROCK_THROW) > 0 && getSkillRechargeTime(Skills.SCISSORS_POKE) < 1)
                myBestSkillChoice = Skills.SCISSORS_POKE;
            else if (powerPlayer.getSkillRechargeTime(Skills.PAPER_CUT) > 0 && getSkillRechargeTime(Skills.ROCK_THROW) < 1)
                myBestSkillChoice = Skills.ROCK_THROW;
            else if (powerPlayer.getSkillRechargeTime(Skills.SCISSORS_POKE) > 0 && getSkillRechargeTime(Skills.PAPER_CUT) < 1)
                myBestSkillChoice = Skills.PAPER_CUT;
            else if(getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) < 1 && (getCurrentRound() == 1
                    || getPlayerInfos().get(myIndex()).getCumulativeRdDifference() >= 0))
                myBestSkillChoice = Skills.REVERSAL_OF_FORTUNE;
            else if (getSkillRechargeTime(Skills.SHOOT_THE_MOON) < 1)
                myBestSkillChoice = Skills.SHOOT_THE_MOON;
            else
                myBestSkillChoice = mySkillPrediction;
        }
        PlayerInfoHolder victim = getPlayerInfos().get(getIndexOfVictim(getPlayerInfos().get(myIndex())));
        List<Skills> possiblePattern = checkForPattern(victim);
        if(possiblePattern != null && (victim.getSkillRechargeTime(Skills.SHOOT_THE_MOON) > 0
                || victim.getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) > 0))
        {
            Skills nextSkillInPattern = nextSkillInPattern(possiblePattern, victim);
            if(nextSkillInPattern != null && nextSkillInPattern != Skills.SHOOT_THE_MOON
                    && nextSkillInPattern != Skills.REVERSAL_OF_FORTUNE)
            {
                if(getSkillRechargeTime(Skills.SHOOT_THE_MOON) < 1)
                {
                    myBestSkillChoice = Skills.SHOOT_THE_MOON;
                    stmSkillPrediction = nextSkillInPattern;
                }
            }
        }
        if(myBestSkillChoice == null)
            myBestSkillChoice = mySkillPrediction;
        return myBestSkillChoice;
    }

    /**
     * Uses Reversal_Of_Fortune if the damage is good, otherwise tries to avoid giving the speed pet any conditional
     * damage. It also manually checks if there have been any recent patterns detected. If so, use Shoot_The_Moon and
     * make our prediction.
     * @param speedPlayer the speed pet that is attacking us.
     * @return our skill choice.
     */
    private Skills bestSkillChoiceAgainstSpeed(PlayerInfoHolder speedPlayer)
    {
        Skills speedPlayerSkillPrediction = getAbilityPredictions().get(speedPlayer);
        Skills mySkillPrediction = getAbilityPredictions().get(getPlayerInfos().get(myIndex()));
        Skills myBestSkillChoice = null;
        if(speedPlayerSkillPrediction == null)
            myBestSkillChoice = mySkillPrediction;
        else if(getPlayerInfos().get(myIndex()).getCumulativeRdDifference() >= 5
                && getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) < 1)
            myBestSkillChoice = Skills.REVERSAL_OF_FORTUNE; // If our cumulative random difference is high, go ahead and use it.
        else
        {
            switch(speedPlayerSkillPrediction)
            {
                case ROCK_THROW:
                    if(mySkillPrediction == Skills.SCISSORS_POKE || mySkillPrediction == Skills.PAPER_CUT)
                    {
                        if(calculateHpPercent() >= 0.75)
                            myBestSkillChoice = Skills.ROCK_THROW;
                        else
                            myBestSkillChoice = mySkillPrediction;
                    }
                    else
                        myBestSkillChoice = mySkillPrediction;
                    break;
                case SCISSORS_POKE:
                    if(mySkillPrediction == Skills.ROCK_THROW || mySkillPrediction == Skills.PAPER_CUT)
                    {
                        if(calculateHpPercent() < 0.75 && calculateHpPercent() >= 0.25)
                            myBestSkillChoice = Skills.SCISSORS_POKE;
                        else
                            myBestSkillChoice = mySkillPrediction;
                    }
                    else
                        myBestSkillChoice = mySkillPrediction;
                    break;
                case PAPER_CUT:
                    if(mySkillPrediction == Skills.SCISSORS_POKE || mySkillPrediction == Skills.ROCK_THROW)
                    {
                        if(calculateHpPercent() < 0.25 && calculateHpPercent() >= 0)
                            myBestSkillChoice = Skills.PAPER_CUT;
                        else
                            myBestSkillChoice = mySkillPrediction;
                    }
                    else
                        myBestSkillChoice = mySkillPrediction;
                    break;
                default:
                    myBestSkillChoice = mySkillPrediction;
                    break;
            }
        }
        PlayerInfoHolder victim = getPlayerInfos().get(getIndexOfVictim(getPlayerInfos().get(myIndex())));
        List<Skills> possiblePattern = checkForPattern(victim);
        if(possiblePattern != null && (victim.getSkillRechargeTime(Skills.SHOOT_THE_MOON) > 0
                || victim.getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) > 0))
        {
            Skills nextSkillInPattern = nextSkillInPattern(possiblePattern, victim);
            if(nextSkillInPattern != null && nextSkillInPattern != Skills.SHOOT_THE_MOON
                    && nextSkillInPattern != Skills.REVERSAL_OF_FORTUNE)
            {
                if(getSkillRechargeTime(Skills.SHOOT_THE_MOON) < 1)
                {
                    myBestSkillChoice = Skills.SHOOT_THE_MOON;
                    stmSkillPrediction = nextSkillInPattern;
                }
            }
        }
        return myBestSkillChoice;
    }

    /**
     * Uses Reversal_Of_Fortune if the damage is good, otherwise just chooses the most damaging ability, since we can't
     * really be punished for picking whatever we want. It also manually checks if there have been any recent patterns
     * detected. If so, use Shoot_The_Moon and make our prediction.
     * @param intelligencePlayer the intelligence pet that is attacking us.
     * @return our skill choice.
     */
    private Skills bestSkillChoiceAgainstIntelligence(PlayerInfoHolder intelligencePlayer)
    {
        Skills mySkillPrediction = getAbilityPredictions().get(getPlayerInfos().get(myIndex()));
        Skills myBestSkillChoice = null;
        if(getPlayerInfos().get(myIndex()).getCumulativeRdDifference() >= 5
                && getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) < 1)
            myBestSkillChoice = Skills.REVERSAL_OF_FORTUNE; // If our cumulative random difference is high, go ahead and use it.

        PlayerInfoHolder victim = getPlayerInfos().get(getIndexOfVictim(getPlayerInfos().get(myIndex())));
        List<Skills> possiblePattern = checkForPattern(victim);
        if(possiblePattern != null && (victim.getSkillRechargeTime(Skills.SHOOT_THE_MOON) > 0
                || victim.getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) > 0))
        {
            Skills nextSkillInPattern = nextSkillInPattern(possiblePattern, victim);
            if(nextSkillInPattern != null && nextSkillInPattern != Skills.SHOOT_THE_MOON
                    && nextSkillInPattern != Skills.REVERSAL_OF_FORTUNE)
            {
                if(getSkillRechargeTime(Skills.SHOOT_THE_MOON) < 1)
                {
                    myBestSkillChoice = Skills.SHOOT_THE_MOON;
                    stmSkillPrediction = nextSkillInPattern;
                }
            }
        }
        if(myBestSkillChoice == null)
            myBestSkillChoice = mySkillPrediction;
        return myBestSkillChoice;
    }

    /**
     * If we have already defined a prediction (from pattern detection), use that prediction, otherwise just use the
     * AiInstance methods to find which skill would offer the pet the most damage.
     * @return out skill prediction.
     */
    @Override
    public Skills getSkillPrediction()
    {
        if(stmSkillPrediction == null)
        {
            int victimIndex = getIndexOfVictim(getPlayerInfos().get(myIndex()));
            PlayerInfoHolder victim = getPlayerInfos().get(victimIndex);
            return getAbilityPredictions().get(victim);
        }
        Skills prediction = stmSkillPrediction;
        stmSkillPrediction = null;
        return prediction;
    }

    /**
     * Simple method for determining where this object is inside the list of PlayerInfoHolders.
     * @return our index in the list.
     */
    private int myIndex()
    {
        for(PlayerInfoHolder player : getPlayerInfos())
        {
            if(player.getPlayableUid() == getPlayableUid())
                return getPlayerInfos().indexOf(player);
        }
        return -1;
    }

    /**
     * Finds out the next skill in a pattern, given a pattern and the player who holds the pattern.
     * @param currentPattern a list of skills representing the predicted pattern.
     * @param player player we're predicting.
     * @return the next skill coming up in the pattern.
     */
    private Skills nextSkillInPattern(List<Skills> currentPattern, PlayerInfoHolder player)
    {
        int currentIndexInPattern = -1;
        List<Skills> allEnemyChoices = getPastAbilitiesChosen().get(player);
        boolean foundCorrectSkill = false;
        Skills lastSkillChoice = allEnemyChoices.get(allEnemyChoices.size() - 1);
        List<Skills> copyOfCurrentPattern = new ArrayList<>(currentPattern);
        while(!foundCorrectSkill)
        {
            // Getting the last time the skill was used inside the pattern.
            currentIndexInPattern = getLastIndexOf(lastSkillChoice, copyOfCurrentPattern);
            boolean correctSkill = true;
            // Checking if our victim used all the skills according to what our current pattern is.
            for(int i = currentIndexInPattern; i >= 0; i --)
            {
                if(copyOfCurrentPattern.get(i) != allEnemyChoices.get(allEnemyChoices.size() - (i + 1)))
                    correctSkill = false;
            }
            if(correctSkill)
                foundCorrectSkill = true;
            else // The pattern doesn't end at this index, so find the previous use of the skill in the pattern.
                copyOfCurrentPattern = createCopyUpToIndex(copyOfCurrentPattern, currentIndexInPattern);
        }
        if(currentIndexInPattern != -1)
            return currentPattern.get((currentIndexInPattern + 1) % currentPattern.size());
        return null;
    }

    /**
     * Finds the last index of the given skill in the given list.
     * @param skillToFind the skill we're trying to find.
     * @param listSearch the list we're searching through.
     * @return the index of the last time the skill has last appeared in the list.
     */
    private int getLastIndexOf(Skills skillToFind, List<Skills> listSearch)
    {
        for(int i = listSearch.size() - 1; i >= 0; i--)
        {
            if(listSearch.get(i) == skillToFind)
                return i;
        }
        return -1;
    }

    /**
     * Copies a given list up to a certain given index.
     * @param listCopyFrom the list being copied.
     * @param copyTo the index being copied up to.
     * @return the copied list.
     */
    private List<Skills> createCopyUpToIndex(List<Skills> listCopyFrom, int copyTo)
    {
        List<Skills> copyList = new ArrayList<>();
        for(int i = 0; i < copyTo; i++)
        {
            copyList.add(listCopyFrom.get(i));
        }
        return copyList;
    }
}
