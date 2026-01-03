
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JOptionPane;
//import javax.sound.sampled.LineUnavailableException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author victoria
 */
public final class Sound {
    public static Clip clip; 
    
    public Sound(){
        String fileName = "sparkle2.wav";
        try {
            playSound(fileName);
            //JOptionPane.showMessageDialog(null, "Press OK to stop playing"); 
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        
    }
    
    public void playSound(String file) throws FileNotFoundException {

        InputStream audioSrc = Sound.class.getResourceAsStream("/" + file);
        if (audioSrc == null) {
            throw new FileNotFoundException("sound.wav not found in resources.");
        }

        // Add a buffered stream for better performance
        InputStream bufferedIn = new BufferedInputStream(audioSrc);

        try {
//            File musicPath = new File(file);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(bufferedIn);
//            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            clip = AudioSystem.getClip();
            clip.open(audioInput); //here
            //clip.
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float vol = gainControl.getValue() + 2.0f;
            gainControl.setValue(vol);

            clip.start();
            clip.loop(-1);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void stopSound(){
        clip.stop();
        clip = null; 
    }
    
}


