package lsafer.edgeseek.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import lsafer.edgeseek.App;
import lsafer.edgeseek.R;
import lsafer.edgeseek.fragment.AboutFragment;
import lsafer.edgeseek.util.Util;

/**
 * An activity that shows the user information about the application.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 04-Jun-20
 */
public class AboutActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(Util.theme(App.data.theme));
		this.setContentView(R.layout.activity_about);

		//app-data fragment
		this.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment_about, new AboutFragment())
				.commit();
	}
}
