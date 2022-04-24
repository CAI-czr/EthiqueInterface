package etu.upmc.ethique.model.component;
public class Switch implements Event {

    private Carriage track1;
    private Carriage track2;
    private Carriage present;

    public Switch(Carriage t1, Carriage t2) {
        track1  = t1;
        track2  = t2;
        present = t1;
        t1.add(this);
        t2.add(this);
    }

    public Switch(Carriage present) {
        track1       = present;
        this.present = present;
        present.add(this);
    }

    public void addCarriage(Carriage carriage) {
        track2 = carriage;
        carriage.add(this);
    }

    public void changeTrack(String track) {
        if (present == track1) {
            present = track2;
        } else if (present == track2) {
            present = track1;
        }
    }

    public Carriage next() { return present.suivant; }
    public Carriage getOtherCarriage(Carriage c) {
        if (c == track1) {
            return track2;
        } else {
            return track1;
        }
    }

    @Override
    public String toString() {
        return "switch: " + track1.toString() + "-" + present.toString();
    }

    public void delCarriage(Carriage c) {
        if (track1 == c) {
            track1 = null;
        } else if (track2 == c) {
            track2 = null;
        }
    }
}
