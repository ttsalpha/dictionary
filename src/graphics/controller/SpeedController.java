package graphics.controller;

import com.voicerss.tts.*;

import java.io.FileOutputStream;

public class SpeedController {

    public static void voice(String input) throws Exception {
        VoiceProvider tts = new VoiceProvider("bec8fd3cb4a744efb53bf494e4083c0d");

        VoiceParameters params = new VoiceParameters(input, Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        tts.addSpeechErrorEventListener(new SpeechErrorEventListener() {
            @Override
            public void handleSpeechErrorEvent(SpeechErrorEvent e) {
                System.out.print(e.getException().getMessage());
            }
        });

        tts.addSpeechDataEventListener(new SpeechDataEventListener() {
            @Override
            public void handleSpeechDataEvent(SpeechDataEvent e) {
                try {
                    byte[] voice = (byte[]) e.getData();

                    FileOutputStream fos = new FileOutputStream("voice.mp3");
                    fos.write(voice, 0, voice.length);
                    fos.flush();
                    fos.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        tts.speechAsync(params);
        tts.speech(params);

        //!!
    }

}