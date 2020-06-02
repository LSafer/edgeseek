package lsafer.edgeseek.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import lsafer.edgeseek.App;
import lsafer.edgeseek.R;
import lsafer.edgeseek.fragment.PermissionsFragment;
import lsafer.edgeseek.util.Util;

/**
 * Activity to manage permissions.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 02-Jun-20
 */
public class PermissionsActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(Util.theme(App.data.theme));
		this.setContentView(R.layout.activity_permissions);

		//app-data fragment
		this.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment_permissions, new PermissionsFragment())
				.commit();
	}
}
