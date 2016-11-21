package com.google.samples.pwm_speaker;

public class MusicNotes {

    public static final double REST = -1;
    public static final double G4 = 392;
    public static final double C5 = 523.25;
    public static final double E5 = 659.25;
    public static final double G5 = 783.99;

    public static final double[] SMB_OVERWORLD_MAIN_THEME = {
            E5, E5, REST, E5, REST, C5, E5, REST, G5, REST, REST, REST, G4
    };

    private MusicNotes() {
        //no instance
    }
}
