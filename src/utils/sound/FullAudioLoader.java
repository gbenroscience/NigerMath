/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils.sound;
import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

/**
 *
 *@author JIBOYE Oluwagbemiro Olaoluwa
 */
public class FullAudioLoader implements Runnable , LineListener  {

        URL url;
	File file;
	AudioInputStream in;
	SourceDataLine line;
	int frameSize;
	byte[] buffer = new byte [32 * 1024]; // 32k is arbitrary
	Thread playThread;
	boolean playing;
	boolean notYetEOF;
/**
 *
 * @param url The url of the sound to be played.
 * @throws IOException
 * @throws UnsupportedAudioFileException
 * @throws LineUnavailableException
 */
	public FullAudioLoader(URL url)
		throws IOException,
			UnsupportedAudioFileException,
			LineUnavailableException {
		this.url = url;
		in = AudioSystem.getAudioInputStream (url);
		AudioFormat format = in.getFormat();
		AudioFormat.Encoding formatEncoding = format.getEncoding();
		if (! (formatEncoding.equals (AudioFormat.Encoding.PCM_SIGNED) ||
			   formatEncoding.equals (AudioFormat.Encoding.PCM_UNSIGNED)))
		   throw new UnsupportedAudioFileException (
                              url.getPath() + " is not PCM audio");
       System.out.println ("got PCM format");
	   frameSize = format.getFrameSize();
	   DataLine.Info info =
		   new DataLine.Info (SourceDataLine.class, format);
	   System.out.println ("got info");
	   line = (SourceDataLine) AudioSystem.getLine (info);
	   System.out.println ("got line");
	   line.open();
	   System.out.println ("opened line");
	   playThread = new Thread (this);
	   playing = false;
	   notYetEOF = true;
	   playThread.start();
	}
        /**
         *
         * @param f The path to the file to be played.
         * @throws IOException
         * @throws UnsupportedAudioFileException
         * @throws LineUnavailableException
         */
	public FullAudioLoader(File f)
		throws IOException,
			UnsupportedAudioFileException,
			LineUnavailableException {
		file = f;
		in = AudioSystem.getAudioInputStream (f);
		AudioFormat format = in.getFormat();
		AudioFormat.Encoding formatEncoding = format.getEncoding();
		if (! (formatEncoding.equals (AudioFormat.Encoding.PCM_SIGNED) ||
			   formatEncoding.equals (AudioFormat.Encoding.PCM_UNSIGNED)))
		   throw new UnsupportedAudioFileException (
                              file.getName() + " is not PCM audio");
       System.out.println ("got PCM format");
	   frameSize = format.getFrameSize();
	   DataLine.Info info =
		   new DataLine.Info (SourceDataLine.class, format);
	   System.out.println ("got info");
	   line = (SourceDataLine) AudioSystem.getLine (info);
	   System.out.println ("got line");
	   line.open();
	   System.out.println ("opened line");
	   playThread = new Thread (this);
	   playing = false;
	   notYetEOF = true;
	   playThread.start();
	}
    @Override
	public void run() {
		int readPoint = 0;
		int bytesRead = 0;

		try {
			while (notYetEOF) {
				if (playing) {
				bytesRead = in.read (buffer,
							 readPoint,
							 buffer.length - readPoint);
                   if (bytesRead == -1) {
				notYetEOF = false;
				break;
				}
				// how many frames did we get,
				// and how many are left over?
				int frames = bytesRead / frameSize;
				int leftover = bytesRead % frameSize;
				// send to line
				line.write (buffer, readPoint, bytesRead-leftover);
				// save the leftover bytes
				System.arraycopy (buffer, bytesRead,
						  buffer, 0,
						  leftover);
                    readPoint = leftover;
				} 
                                else {
				try { 
                                    Thread.sleep (10);
                                }
				catch (InterruptedException ie) {}
				}
			} // while notYetEOF
			System.out.println ("reached eof");
			line.drain();
			line.stop();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			// line.close();
		}
	} // run

	public void start() {
		playing = true;
		if (! playThread.isAlive())
			playThread.start();
		line.start();
	}

	public void stop() {
		playing = false;
		line.stop();
	}

	public SourceDataLine getLine() {
		return line;
	}

	public File getFile() {
		return file;
	}

public void play(){
	try {
                getLine().addLineListener(this);
                start();
	} catch (Exception e) {
		e.printStackTrace();
	}


}

// LineListener
    @Override
public void update (LineEvent le) {
	LineEvent.Type type = le.getType();
	if (type == LineEvent.Type.OPEN) {
		System.out.println ("OPEN");
	} else if (type == LineEvent.Type.CLOSE) {
		System.out.println ("CLOSE");
		System.exit (0);
	}
        else if (type == LineEvent.Type.START) {
		System.out.println ("START");
//insert actions that should occur when the music is about to start.
	}
        else if (type == LineEvent.Type.STOP) {
		System.out.println ("STOP");
		line.close();
//insert actions that should occur when the music is about to stop.
	}
  }
        	public static void main (String[] args) {

	try {
		FullAudioLoader loader = new FullAudioLoader(new File(
                        "C:/Users/MRSJIBOYE/Music/ENTERTAINING GOD/Psalms, Hymns And Spiritual Songs/Gbenro's Special/05 Micah Stampley - Holiness (Take My Life).wav"
                        ));
                loader.play();
	} catch (Exception e) {
		e.printStackTrace();
	}
}

}


