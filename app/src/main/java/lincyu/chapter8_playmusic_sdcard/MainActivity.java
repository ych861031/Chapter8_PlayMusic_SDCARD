package lincyu.chapter8_playmusic_sdcard;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity {
	
	private MediaPlayer player;
	
	private File file;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView tv_song = (TextView)findViewById(R.id.tv_song);

		Button btn_start = (Button)findViewById(R.id.btn_start);
		btn_start.setOnClickListener(l_start);
		Button btn_pause = (Button)findViewById(R.id.btn_pause);
		btn_pause.setOnClickListener(l_pause);
		Button btn_stop = (Button)findViewById(R.id.btn_stop);
		btn_stop.setOnClickListener(l_stop);
		
		file = searchsong();
		if (file == null) {
			tv_song.setText("無法找到可播放的歌曲");
			btn_start.setVisibility(View.GONE);
			btn_pause.setVisibility(View.GONE);
			btn_stop.setVisibility(View.GONE);
		} else {
			tv_song.setText(file.getName());
		}
	}

	File searchsong() {
		File sdcard = Environment.getExternalStorageDirectory();
//		File sdcard = new File("/storage/1142-FB4A/doc/");
		Toast.makeText(this, sdcard.toString(), Toast.LENGTH_LONG).show();
		File files [] = sdcard.listFiles();

		if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f.isFile() == false) continue;
                String name = f.getName();
                int start = name.lastIndexOf('.');
                String subname = name.substring(start + 1);
                if (subname.equalsIgnoreCase("mp3")) {
                    return f;
                }
            }
        }
		return null;
	}

	@Override
	public void onResume() {
		super.onResume();
		try {
			player = new MediaPlayer();
			player.setDataSource(file.getPath());
			player.setOnCompletionListener(comL);
			player.prepare();
		} catch (Exception e) {
		}
	}

	public void onPause() {
		super.onPause();
		player.release();
	}

	private OnClickListener l_start = new OnClickListener() {
		public void onClick(View v) {
			try {
				player.start();
			} catch (Exception e) {
			}
		}
	};

	private OnClickListener l_pause = new OnClickListener() {
		public void onClick(View v) {
			try {
				if(player.isPlaying() == false) return;
				player.pause();
			} catch (Exception e) {
			}
		}
	};

	private OnClickListener l_stop = new OnClickListener() {
		public void onClick(View v) {
			try {
				player.stop();
				player.prepare();
			} catch (Exception e) {
			}
		}
	};

	private OnCompletionListener comL = new OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer nouse) {
			try {
				player.stop();
				player.prepare();
			} catch (Exception e){
			}
		}
	};
}
