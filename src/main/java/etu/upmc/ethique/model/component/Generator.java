package etu.upmc.ethique.model.component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;
//import javax.swing.JPanel;

public class Generator {
    public static final Pattern PATTERN = Pattern.compile("[^(;),]+");
    public static final String TRACK = "track";
    public static final String NUMBERINGROUP = "numberInGroup";
    public static final String GROUP = "group";
    public static final String OBJECT = "object";
    public static final String TIME = "time";
    public static final String INITIALLY = "initially";
    public static final String BRIDGEON = "bridgeOn";
    public static final String BOTTONON = "bottonOn";
    public static final String TRAIN = "train";
    public static final String ON = "on";
    public static final String ALIVE = "alive";

    public static final String EFFECT = "effect";
    public static final String PREC = "prec";
    public static final String ACT = "act";
    public static final String SWITCH = "switch";

    public static final String NEG = "neg";
    public static final String END = ".\n";
    public static final String SEPARATOR = "-------------";

    public final ArrayList<Track> tracks;
    public final ArrayList<Switch> switchs;
    public final ArrayList<Bridge> bridges;
    public final ArrayList<Group> groups;
    public final Train train;
    private int time;
    private boolean initial;
//    private JPanel                panel = null;

    public Generator(String fileName) {
        this();
        initial = true;
        read(fileName);
        initial = false;
    }

    public Generator(File file) {
        this();
        initial = true;
        readFile(file);
        initial = false;
        System.out.println("The final reading:\n" + toString());
    }

//    public void addPanel(JPanel panel) { this.panel = panel; }

    public void read(String fileName) {
        printWithSeparator("Begin of creation");
        readFile(fileName);
        printWithSeparator("End of creation");
        System.out.println("The final reading:\n" + toString());
    }

    public void readFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                ++lineNumber;
                String line = scanner.nextLine();
                if (((Pattern.compile("%.+%").matcher(line)).find()) || (line == "")) {
                    System.out.println("Skip line " + lineNumber);
                    continue;
                }
                System.out.println("begin to read line " + lineNumber + " : " + line);
                Matcher matcher = PATTERN.matcher(line);
                if (matcher.find()) read_information(matcher, line);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("File does not exist or an unknown error has occurred");
        }
    }

    public void readFile(String fileName) {
        try {
            Path path = Paths.get(fileName);
            File file = new File(fileName);
            readFile(file);
        } catch (Exception e) {
            System.out.println("File does not exist or an unknown error has occurred");
        }
    }

    public String combineContent(String s1, String s2) {
        return s1 + "(" + s2 + ")";
    }

    public String writeComponentTrolley(ComponentTrolley[] listComponentTrolleys) {
        String content = "";
        for (int i = 0; i < listComponentTrolleys.length; i++) {
            if (i != 0) content += ";";
            content += listComponentTrolleys[i].getInformation();
        }
        return content;
    }

    public void save(String fileName) {
        try {
            File file = new File("./source/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            saveFile(file);
        } catch (Exception e) {
        }
    }

    public void saveFile(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write("% " + SEPARATOR + "DOMAINS" + SEPARATOR + " %\n");
            // time
            out.write(combineContent(TIME, generateNumber(time)) + END);
            // track
            out.write(combineContent(
                    TRACK, writeComponentTrolley(tracks.toArray(new Track[tracks.size()]))) +
                    END);
            // group
            String content = "";
            for (int i = 0; i < groups.size(); i++) {
                if (i != 0) content += ";";
                content += groups.get(i).getName();
            }
            out.write(combineContent(GROUP, content) + END);
            // NumberIngroup
            out.write(
                    combineContent(NUMBERINGROUP,
                            writeComponentTrolley(groups.toArray(new Group[groups.size()]))) +
                            END);
            // Bridge
            out.write(
                    combineContent(
                            BRIDGEON, writeComponentTrolley(bridges.toArray(new Bridge[bridges.size()]))) +
                            END);
            out.write("% " + SEPARATOR + "INITIAL SITUATION" + SEPARATOR + " %\n");
            // position of groups
            content = "";
            for (int i = 0; i < groups.size(); i++) {
                if (i != 0) content += ";";
                if (groups.get(i).getPosition() != null) {
                    content +=
                            groups.get(i).getName() + "," + groups.get(i).getPosition().getName();
                }
            }
            out.write(combineContent(INITIALLY, combineContent(ON, content)) + END);
            // groups alive
            content = "";
            for (int i = 0; i < groups.size(); i++) {
                if (i != 0) content += ";";
                if (groups.get(i).isAlive()) content += groups.get(i).getName();
            }
            out.write(combineContent(INITIALLY, combineContent(ALIVE, content)) + END);
            // initial position of train
            out.write(combineContent(
                    INITIALLY,
                    combineContent(ON, TRAIN + "," + train.getOriginPosition().getName())) +
                    END);
            out.close();
        } catch (IOException e) {
        }
    }

    public void reset() {
        train.setPresent(train.getOriginPosition());
        for (Group group : groups) {
            group.setAlive(true);
        }
    }

    public void simulation() {
        int i = 0;
        printWithSeparator("Simulation begins");
        System.out.println("train sur " + train.getOriginPosition().getName());
        reset();
        while ((i++ < time) && (train.getPresent() != null)) {
            train.run();
        }
        printWithSeparator("Simulation ends");
    }

    public void read_information(Matcher matcher, String line) {
        switch (matcher.group()) {
            case TRACK -> readTrack(matcher);
//            case OBJECT->break;
            case GROUP -> readGroups(matcher);
            case NUMBERINGROUP -> readNumberInGroup(matcher);
            case BRIDGEON -> readBridges(matcher);
            case TIME -> time = Math.max(time, readNumber(line)[1]);
            case INITIALLY -> readInitial(matcher);
            case ACT -> readAct(matcher);
            case PREC -> readPrec(matcher);
            case EFFECT -> readEffect(matcher);
        }
    }

    public void readEffect(Matcher matcher) {
        matcher.usePattern(Pattern.compile("([^,]+),([^)]+)"));
        if (matcher.find()) {
            String Sevent = matcher.group(1);
            String consequance = matcher.group(2);
            System.out.println("try to read effect of " + Sevent + " cause " + consequance);
            Event event = readEvent(Sevent);
            readConsequance(event, consequance);
        }
    }

    public void readConsequance(Event event, String consequance) {
        Matcher matcher = PATTERN.matcher(consequance);
        System.out.println("try to read Consequance " + consequance);
        if (event instanceof Switch) {
            Switch sw = (Switch) event;
            matcher.find();
            if (matcher.group().equals("on")) {
                matcher.find();
                Carriage car = (Carriage) readPosition(matcher);
                sw.addCarriage(car);
                sw.changeTrack(car.getTrack());
                System.out.println("Train will be Switched to " + car.toString());
            }
        }
    }

    public Event readEvent(String event) {
        Matcher matcher = PATTERN.matcher(event);
        if (matcher.find()) {
            String key = matcher.group();
            switch (key) {
                case SWITCH:
                    System.out.println("try to read switch");
                    Position pos = readPosition(matcher);
                    if (((Carriage) pos).getSwitch() != null) {
                        System.out.println("Event is read as Switch on " + pos.toString());
                        return ((Carriage) pos).getSwitch();
                    }
                    break;
                default:
                    break;
            }
        }
        return null;
    }

    public void readPrec(Matcher matcher) {
        matcher.usePattern(Pattern.compile("([^,]+),([^,]+)"));
        if (matcher.find()) {
            String preCondtion = matcher.group(1);
            String event = matcher.group(2);
        }
    }

    public void readAct(Matcher matcher) {
        if (matcher.find()) {
            switch (matcher.group()) {
                case SWITCH:
                    readSwitch(matcher);
                    break;
                default:
                    break;
            }
        }
    }

    public void readInitial(Matcher matcher) {
        if (matcher.find()) {
            switch (matcher.group()) {
                case ON:
                    readOn(matcher);
                    break;
                case ALIVE:
                    readAlive(matcher);
                    break;
                default:
                    break;
            }
        }
    }

    public Switch readSwitch(Matcher matcher) {
        matcher.usePattern(Pattern.compile("([a-z]+)\\(([0-9]+)"));
        if (matcher.find()) {
            Carriage position = (Carriage) readPosition(matcher.group(1), matcher.group(2));
            System.out.println("add Switch on " + position.toString());
            if (position.getSwitch() == null) {
                Switch sw = new Switch((Carriage) position);
                switchs.add(sw);
            }
            return position.getSwitch();
        }
        return null;
    }

    public static String generateNumber(int max) {
        return "0..." + max;
    }

    public static int find(ComponentTrolley[] l, String name) {
        for (int i = 0; i < l.length; i++) {
            if (l[i].getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public void readTrack(Matcher m) {
        int num = -1;
        String name;
        while (m.find()) {
            name = m.group();
            if (m.find()) {
                num = readNumber(m.group())[1];
                tracks.add(new Track(name, num));
                System.out.printf("add %s(%d)\n", name, num);
            }
        }
    }

    public int[] readNumber(String num) {
        Pattern numberPattern = Pattern.compile("[0-9]+");
        Matcher matcher = numberPattern.matcher(num);
        int[] number = new int[2];
        matcher.find();
        number[0] = Integer.valueOf(matcher.group());
        if (matcher.find())
            number[1] = Integer.valueOf(matcher.group());
        else
            number[1] = number[0];
        return number;
    }

    public void readGroups(Matcher m) {
        while (m.find() && !m.group().equals(".")) {
            groups.add(new Group(m.group()));
            System.out.printf("add group %s\n", m.group());
        }
    }

    public void readNumberInGroup(Matcher m) {
        m.usePattern(Pattern.compile("([a-z0-9]+),([0-9]+)")); // numOrAlphabet,num
        while ((m.find()) && (m.groupCount() == 2)) {
            int i = find(groups.toArray(new Group[groups.size()]), m.group(1));
            if (i != -1) {
                groups.get(i).setNb(Integer.valueOf(m.group(2)));
                System.out.printf("set group %s as %s persons\n", groups.get(i).getName(),
                        m.group(2));
            }
        }
    }

    public Position readPosition(Matcher matcher) {
        matcher.find();
        String position = matcher.group();
        matcher.find();
        String number = matcher.group();
        return readPosition(position, number);
    }

    public Position readPosition(String position, String number) {
        int index;
        if (number != "") {
            try {
                int n = Integer.valueOf(number);
                if ((index = find(tracks.toArray(new Track[tracks.size()]), position)) != -1) {
                    return tracks.get(index).get(n);
                }
            } catch (Exception e) {
                System.out.println("bad format");
            }
        } else if ((index = find(bridges.toArray(new Bridge[bridges.size()]), position)) != -1) {
            return bridges.get(index);
        }
        return null;
    }

    public boolean isInitial() {
        return initial;
    }

    public void readOn(Matcher matcher) {
        Pattern on = Pattern.compile("([a-z0-9]+),([a-z]+)\\(*([0-9]*)");
        matcher.usePattern(on);
        while (matcher.find()) {
            System.out.println("try to read the position : " + matcher.group(2) +
                    matcher.group(3));
            String name = matcher.group(1);
            Position position = readPosition(matcher.group(2), matcher.group(3));
            int index;
            if (name.equals(TRAIN)) {
                if (initial || train.getOriginPosition() == null)
                    train.setOriginPosition((Carriage) position);
                else
                    train.setPresent((Carriage) position);
            } else if ((index = find(groups.toArray(new Group[groups.size()]), name)) != -1) {
                groups.get(index).setPosition(position);
            } else {
                bridges.add(new Bridge(name, position));
            }
            System.out.printf("put %s on %s\n", name, position);
        }
    }

    public void readBridges(Matcher matcher) {
        readOn(matcher);
    }

    public void readAlive(Matcher matcher) {
        int index;
        while (matcher.find()) {
            if ((index = find(groups.toArray(new Group[groups.size()]), matcher.group())) != -1) {
                groups.get(index).setAlive(true);
                System.out.println("set group " + matcher.group() + " alive");
            }
        }
    }

    public static void printWithSeparator(String info) {
        System.out.println("\n" + SEPARATOR + info + SEPARATOR + "\n");
    }

    @Override
    public String toString() {
        String out = "";
        for (Track track : tracks) {
            out += track + "\n";
        }
        for (Group group : groups) {
            out += group + "\n";
        }
        if (switchs != null) {
            for (Switch s : switchs) {
                out += s + "\n";
            }
        }
        if (bridges != null) {
            for (Bridge bridge : bridges) {
                out += bridge + "\n";
            }
        }
        if (train != null) out += train + "\n";
        return out;
    }

    public void addNumberInGroup(String nom, int n) {
        int i = Generator.find(groups.toArray(new Group[groups.size()]), nom);
        if (i != -1) {
            groups.get(i).setNb(n);
            System.out.printf("set group %s as %s persons\n", groups.get(i).getName(), n);
        }
    }

    public Generator() {
        train = new Train();
        tracks = new ArrayList<Track>();
        switchs = new ArrayList<Switch>();
        bridges = new ArrayList<Bridge>();
        groups = new ArrayList<Group>();
    }

    public void setTime(int n) {
        time = n;
    }

    public int getTime() {
        return time;
    }

    public void removeCarriage(Carriage carriage) {
        for (Track track : tracks) {
            if (track.getName().equals(carriage.getTrack())) {
                int idx = track.indexOf(carriage);
                if (idx != 0 && idx != track.size() - 1) {
                    track.get(idx - 1).suivant = track.get(idx + 1);
                }
                track.remove(carriage);
            }
        }
    }

}
