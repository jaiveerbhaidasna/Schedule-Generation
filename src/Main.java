// Schedule Generator

import java.util.*;

public class Main {

    private int numTeams;
    private boolean conferences;
    private String[] conference1;
    private String[] conference2;
    private ArrayList<String> c1 = new ArrayList<>();
    private ArrayList<String> c2 = new ArrayList<>();
    private int innerGames;
    private int outerGames;
    private int gamesPerRound;
    private int roundCounter;

    public static void main(String[] args)
    {
        Main gen = new Main();

        gen.pollUser();

        gen.outputSchedule();

    }

    public void pollUser()
    {
        System.out.println("Welcome to the schedule generator!");
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter number of teams: ");
        numTeams = Integer.valueOf(scan.next());

        System.out.print("Do you want conferences? (Y/N):  ");
        String conf = scan.next();
        if(conf.equals("Y"))
            conferences = true;
        else
            conferences = false;

        System.out.println("Enter the teams for conference one separated by commas: ");
        String conf1 = scan.next();
        conference1 = conf1.split(",");
        for(int i = 0; i < conference1.length; i++)
            c1.add(conference1[i]);

        System.out.print("Enter the number of games versus each team in conference: ");
        innerGames = Integer.valueOf(scan.next());

        if(conferences)
        {
            System.out.println("Enter the teams for conference two separated by commas: ");
            String conf2 = scan.next();
            conference2 = conf2.split(",");
            for(int i = 0; i < conference2.length; i++)
                c2.add(conference2[i]);

            System.out.print("Enter the number of games versus each team out of conference: ");
            outerGames = Integer.valueOf(scan.next());

        }

        System.out.print("Enter the number of games you'd like to be played in each round: ");
        gamesPerRound = Integer.valueOf(scan.next());

        System.out.println();

    }

    public void outputSchedule()
    {
        roundCounter = 1;
        int roundGames = 0;
        String roundOutput = "";
        ArrayList<String> duplicates = new ArrayList<>();

        if(!conferences)
        {
            boolean home = true;

            while(innerGames > 0)
            {
                for (int i = 0; i < numTeams - 1; i++) {
                    for (int j = 1; j < numTeams; j += 2)
                    {
                        if (home)
                        {
                            if (roundOutput.indexOf(c1.get(j - 1)) >= 0 || roundOutput.indexOf(c1.get(j)) >= 0) {
                                duplicates.add(c1.get(j - 1));
                                duplicates.add(c1.get(j));
                            }
                            else
                            {
                                roundOutput += c1.get(j - 1) + " @ " + c1.get(j) + "\n";
                                roundGames++;
                                if (checkRoundComplete(roundGames))
                                {
                                    roundCounter = printRound(roundOutput, roundCounter);
                                    roundGames = (duplicates.size() % 5) / 2;
                                    roundOutput = fixDuplicates(duplicates);
                                    duplicates.clear();
                                }
                            }
                        }
                        else
                        {
                            if (roundOutput.indexOf(c1.get(j - 1)) >= 0 || roundOutput.indexOf(c1.get(j)) >= 0) {
                                duplicates.add(c1.get(j));
                                duplicates.add(c1.get(j - 1));
                            }
                            else
                            {
                                roundOutput += c1.get(j) + " @ " + c1.get(j-1) + "\n";
                                roundGames++;
                                if (checkRoundComplete(roundGames))
                                {
                                    roundCounter = printRound(roundOutput, roundCounter);
                                    roundGames = (duplicates.size() % 5) / 2;
                                    roundOutput = fixDuplicates(duplicates);
                                    duplicates.clear();
                                }
                            }
                        }


                    }
                    home = !home;
                    rotateEven();
                }

                if(roundGames != 0)
                {
                    roundGames = 0;
                    roundCounter = printRound(roundOutput, roundCounter);
                    roundOutput = "";
                }

                innerGames--;
            }
        }

        else
        {
            if((numTeams / 2) % 2 == 1)
            {
                boolean inner = true;
                boolean home = true;
                boolean outerAway = true;

                while(innerGames > 0 || outerGames > 0)
                {
                    if(inner && innerGames > 0)
                    {
                        for(int i = 0; i < numTeams/2; i++)
                        {
                            for(int j = 1; j < numTeams/2; j+=2)
                            {
                                if(home)
                                {
                                    if(roundOutput.indexOf(c1.get(j-1)) >= 0 || roundOutput.indexOf(c1.get(j)) >= 0)
                                    {
                                        duplicates.add(c1.get(j-1));
                                        duplicates.add(c1.get(j));
                                    }
                                    else
                                    {
                                        roundOutput += c1.get(j-1) + " @ " + c1.get(j) + "\n";
                                        roundGames++;
                                        if(checkRoundComplete(roundGames))
                                        {
                                            roundCounter = printRound(roundOutput, roundCounter);
                                            roundGames = (duplicates.size() % 5) / 2;
                                            roundOutput = fixDuplicates(duplicates);
                                            duplicates.clear();
                                        }
                                    }
                                    if(roundOutput.indexOf(c2.get(j-1)) >= 0 || roundOutput.indexOf(c2.get(j)) >= 0)
                                    {
                                        duplicates.add(c2.get(j-1));
                                        duplicates.add(c2.get(j));
                                    }
                                    else
                                    {
                                        roundOutput += c2.get(j-1) + " @ " + c2.get(j) + "\n";
                                        roundGames++;
                                        if(checkRoundComplete(roundGames))
                                        {
                                            roundCounter = printRound(roundOutput, roundCounter);
                                            roundGames = (duplicates.size() % 5) / 2;
                                            roundOutput = fixDuplicates(duplicates);
                                            duplicates.clear();
                                        }
                                    }

                                }
                                else
                                {
                                    if(roundOutput.indexOf(c1.get(j-1)) >= 0 || roundOutput.indexOf(c1.get(j)) >= 0)
                                    {
                                        duplicates.add(c1.get(j-1));
                                        duplicates.add(c1.get(j));
                                    }
                                    else
                                    {
                                        roundOutput += c1.get(j) + " @ " + c1.get(j-1) + "\n";
                                        roundGames++;
                                        if(checkRoundComplete(roundGames))
                                        {
                                            roundCounter = printRound(roundOutput, roundCounter);
                                            roundGames = (duplicates.size() % 5) / 2;
                                            roundOutput = fixDuplicates(duplicates);
                                            duplicates.clear();
                                        }
                                    }

                                    if(roundOutput.indexOf(c2.get(j-1)) >= 0 || roundOutput.indexOf(c2.get(j)) >= 0)
                                    {
                                        duplicates.add(c2.get(j-1));
                                        duplicates.add(c2.get(j));
                                    }
                                    else
                                    {
                                        roundOutput += c2.get(j) + " @ " + c2.get(j-1) + "\n";
                                        roundGames++;
                                        if(checkRoundComplete(roundGames))
                                        {
                                            roundCounter = printRound(roundOutput, roundCounter);
                                            roundGames = (duplicates.size() % 5) / 2;
                                            roundOutput = fixDuplicates(duplicates);
                                            duplicates.clear();
                                        }
                                    }

                                }
                            }
                            rotateOdd();
                        }

                        if(roundGames != 0)
                        {
                            roundGames = 0;
                            roundCounter = printRound(roundOutput, roundCounter);
                            roundOutput = "";
                        }

                        innerGames--;
                        home = !home;
                    }
                    else if(outerGames > 0)
                    {
                        for(int i = 0; i < numTeams / 2; i++)
                        {
                            for(int j = (numTeams / 2) - 1; j >= 0; j--)
                            {
                                if(outerAway)
                                {
                                    if(roundOutput.indexOf(c1.get(j)) >= 0 || roundOutput.indexOf(c2.get(j)) >= 0)
                                    {
                                        duplicates.add(c1.get(j));
                                        duplicates.add(c2.get(j));
                                    }
                                    else
                                    {
                                        roundOutput += c1.get(j) + " @ " + c2.get(j) + "\n";
                                        roundGames++;
                                        if(checkRoundComplete(roundGames))
                                        {
                                            roundCounter = printRound(roundOutput, roundCounter);
                                            roundGames = (duplicates.size() % 5) / 2;
                                            roundOutput = fixDuplicates(duplicates);
                                            duplicates.clear();
                                        }
                                    }
                                }
                                else
                                {
                                    if(roundOutput.indexOf(c2.get(j)) >= 0 || roundOutput.indexOf(c1.get(j)) >= 0)
                                    {
                                        duplicates.add(c2.get(j));
                                        duplicates.add(c1.get(j));
                                    }
                                    else
                                    {
                                        roundOutput += c2.get(j) + " @ " + c1.get(j) + "\n";
                                        roundGames++;
                                        if(checkRoundComplete(roundGames))
                                        {
                                            roundCounter = printRound(roundOutput, roundCounter);
                                            roundGames = (duplicates.size() % 5) / 2;
                                            roundOutput = fixDuplicates(duplicates);
                                            duplicates.clear();
                                        }
                                    }
                                }

                            }

                            outerAway = !outerAway;

                            String temp = c2.remove(0);
                            c2.add(temp);
                        }

                        if(roundGames != 0)
                        {
                            roundGames = 0;
                            roundCounter = printRound(roundOutput, roundCounter);
                            roundOutput = "";
                        }


                        outerGames--;

                    }
                    outerAway = !outerAway;
                    inner = !inner;
                }

                //printRound(roundOutput, roundCounter);

            }
            else
            {
                boolean inner = true;
                boolean home = true;
                boolean outerAway = true;

                while(innerGames > 0 || outerGames > 0)
                {
                    if(inner && innerGames > 0)
                    {
                        for(int i = 0; i < numTeams/2 - 1; i++)
                        {
                            for(int j = 1; j < numTeams/2; j+=2)
                            {
                                if(home)
                                {
                                    if(roundOutput.indexOf(c1.get(j-1)) >= 0 || roundOutput.indexOf(c1.get(j)) >= 0)
                                    {
                                        duplicates.add(c1.get(j-1));
                                        duplicates.add(c1.get(j));
                                    }
                                    else
                                    {
                                        roundOutput += c1.get(j-1) + " @ " + c1.get(j) + "\n";
                                        roundGames++;
                                        if(checkRoundComplete(roundGames))
                                        {
                                            roundCounter = printRound(roundOutput, roundCounter);
                                            roundGames = (duplicates.size() % 5) / 2;
                                            roundOutput = fixDuplicates(duplicates);
                                            duplicates.clear();
                                        }
                                    }
                                    if(roundOutput.indexOf(c2.get(j-1)) >= 0 || roundOutput.indexOf(c2.get(j)) >= 0)
                                    {
                                        duplicates.add(c2.get(j-1));
                                        duplicates.add(c2.get(j));
                                    }
                                    else
                                    {
                                        roundOutput += c2.get(j-1) + " @ " + c2.get(j) + "\n";
                                        roundGames++;
                                        if(checkRoundComplete(roundGames))
                                        {
                                            roundCounter = printRound(roundOutput, roundCounter);
                                            roundGames = (duplicates.size() % 5) / 2;
                                            roundOutput = fixDuplicates(duplicates);
                                            duplicates.clear();
                                        }
                                    }

                                }
                                else
                                {
                                    if(roundOutput.indexOf(c1.get(j-1)) >= 0 || roundOutput.indexOf(c1.get(j)) >= 0)
                                    {
                                        duplicates.add(c1.get(j-1));
                                        duplicates.add(c1.get(j));
                                    }
                                    else
                                    {
                                        roundOutput += c1.get(j) + " @ " + c1.get(j-1) + "\n";
                                        roundGames++;
                                        if(checkRoundComplete(roundGames))
                                        {
                                            roundCounter = printRound(roundOutput, roundCounter);
                                            roundGames = (duplicates.size() % 5) / 2;
                                            roundOutput = fixDuplicates(duplicates);
                                            duplicates.clear();
                                        }
                                    }

                                    if(roundOutput.indexOf(c2.get(j-1)) >= 0 || roundOutput.indexOf(c2.get(j)) >= 0)
                                    {
                                        duplicates.add(c2.get(j-1));
                                        duplicates.add(c2.get(j));
                                    }
                                    else
                                    {
                                        roundOutput += c2.get(j) + " @ " + c2.get(j-1) + "\n";
                                        roundGames++;
                                        if(checkRoundComplete(roundGames))
                                        {
                                            roundCounter = printRound(roundOutput, roundCounter);
                                            roundGames = (duplicates.size() % 5) / 2;
                                            roundOutput = fixDuplicates(duplicates);
                                            duplicates.clear();
                                        }
                                    }

                                }
                            }
                            rotateEven();
                            home = !home;
                        }

                        if(roundGames != 0)
                        {
                            roundGames = 0;
                            roundCounter = printRound(roundOutput, roundCounter);
                            roundOutput = "";
                        }

                        innerGames--;
                        home = !home;
                        //System.out.println("\nDONE WITH INNER CONFERENCE " + innerGames + "\n");
                    }
                    else if(outerGames > 0)
                    {
                        for(int i = 0; i < numTeams / 2; i++)
                        {
                            for(int j = (numTeams / 2) - 1; j >= 0; j--)
                            {
                                if(outerAway)
                                {
                                    if(roundOutput.indexOf(c1.get(j)) >= 0 || roundOutput.indexOf(c2.get(j)) >= 0)
                                    {
                                        duplicates.add(c1.get(j));
                                        duplicates.add(c2.get(j));
                                    }
                                    else
                                    {
                                        roundOutput += c1.get(j) + " @ " + c2.get(j) + "\n";
                                        roundGames++;
                                        if(checkRoundComplete(roundGames))
                                        {
                                            roundCounter = printRound(roundOutput, roundCounter);
                                            roundGames = (duplicates.size() % 5) / 2;
                                            roundOutput = fixDuplicates(duplicates);
                                            duplicates.clear();
                                        }
                                    }
                                }
                                else
                                {
                                    if(roundOutput.indexOf(c2.get(j)) >= 0 || roundOutput.indexOf(c1.get(j)) >= 0)
                                    {
                                        duplicates.add(c2.get(j));
                                        duplicates.add(c1.get(j));
                                    }
                                    else
                                    {
                                        roundOutput += c2.get(j) + " @ " + c1.get(j) + "\n";
                                        roundGames++;
                                        if(checkRoundComplete(roundGames))
                                        {
                                            roundCounter = printRound(roundOutput, roundCounter);
                                            roundGames = (duplicates.size() % 5) / 2;
                                            roundOutput = fixDuplicates(duplicates);
                                            duplicates.clear();
                                        }
                                    }
                                }

                            }

                            outerAway = !outerAway;

                            String temp = c2.remove(0);
                            c2.add(temp);
                        }

                        if(roundGames != 0)
                        {
                            roundGames = 0;
                            roundCounter = printRound(roundOutput, roundCounter);
                            roundOutput = "";
                        }

                        outerGames--;
                        //System.out.println("\nDONE WITH OUTER CONFERENCE " + outerGames + "\n");

                    }
                    outerAway = !outerAway;
                    inner = !inner;
                }
            }
        }


    }

    public int printRound(String round, int counter)
    {
        System.out.println("R" + counter);
        System.out.println(round);
        return counter + 1;
    }

    public boolean checkRoundComplete(int finished)
    {
        return finished == gamesPerRound;
    }

    public void rotateOdd()
    {
        for(int i = 0; i < c1.size(); i++)
        {
            if(i == 0)
                conference1[i+1] = c1.get(i);
            else if(i == (c1.size() - 2))
                conference1[conference1.length - 1] = c1.get(i);
            else if(i % 2 == 0)
                conference1[i-2] = c1.get(i);
            else
                conference1[i+2] = c1.get(i);
        }

        c1.clear();
        for(int i = 0; i < conference1.length; i++)
            c1.add(conference1[i]);


        if(conferences)
        {
            for(int i = 0; i < c2.size(); i++)
            {
                if(i == 0)
                    conference2[i+1] = c2.get(i);
                else if(i == (c2.size() - 2))
                    conference2[conference2.length - 1] = c2.get(i);
                else if(i % 2 == 0)
                    conference2[i-2] = c2.get(i);
                else
                    conference2[i+2] = c2.get(i);
            }

            c2.clear();
            for(int i = 0; i < conference2.length; i++)
                c2.add(conference2[i]);
        }

    }

    public void rotateEven()
    {

        conference1[0] = c1.get(0);
        for(int i = 1; i < c1.size(); i++)
        {
            if(i == (c1.size() - 1))
                conference1[conference1.length - 2] = c1.get(i);
            else if(i == 2)
                conference1[1] = c1.get(i);
            else if(i % 2 == 0)
                conference1[i-2] = c1.get(i);
            else
                conference1[i+2] = c1.get(i);
        }

        c1.clear();
        for(int i = 0; i < conference1.length; i++)
            c1.add(conference1[i]);

        if(conferences)
        {
            conference2[0] = c2.get(0);
            for(int i = 1; i < c2.size(); i++)
            {
                if(i == (c2.size() - 1))
                    conference2[conference2.length - 2] = c2.get(i);
                else if(i == 2)
                    conference2[1] = c2.get(i);
                else if(i % 2 == 0)
                    conference2[i-2] = c2.get(i);
                else
                    conference2[i+2] = c2.get(i);
            }

            c2.clear();
            for(int i = 0; i < conference2.length; i++)
                c2.add(conference2[i]);
        }

    }

    public String fixDuplicates(ArrayList<String> duplicates)
    {
        /*
        System.out.println("DUPLICATE LIST:");
        for(int i = 0; i < duplicates.size(); i+=2)
        {
            //System.out.print(duplicates.get(i) + " @ " + duplicates.get(i+1) + "\n");
        }
        System.out.println();
        */

        String round = "";
        int count = 0;

        for(int i = 0; i < duplicates.size(); i+=2)
        {
            //count++;
            round += duplicates.get(i) + " @ " + duplicates.get(i+1) + "\n";
            /*
            if(count == gamesPerRound)
            {
                //roundCounter = printRound(round, roundCounter);
                //round = "";
                //count = 0;
            }
            */
        }

        return round;
    }


}

// 01A,02A,03A,04A,05A,06A,07A,08A
// 01B,02B,03B,04B,05B,06B,07B,08B
