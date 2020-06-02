/*
 *	Copyright 2020 LSafer
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
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
