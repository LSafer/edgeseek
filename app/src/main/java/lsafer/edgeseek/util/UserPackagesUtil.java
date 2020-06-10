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
package lsafer.edgeseek.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * An utils for dealing with the user packages.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 09-Jun-20
 */
final public class UserPackagesUtil {
	/**
	 * The applications.
	 */
	private static ApplicationInfo[] applications;
	/**
	 * The labels.
	 */
	private static CharSequence[] labels;
	/**
	 * The packages' names.
	 */
	private static CharSequence[] packages;

	/**
	 * This is an util class. And must not be instanced as an object.
	 *
	 * @throws AssertionError when called.
	 */
	private UserPackagesUtil() {
		throw new AssertionError("No instance for you!");
	}

	/**
	 * Get the info of all applications installed in the user's device.
	 *
	 * @param manager to be used.
	 * @return the info of all the installed applications.
	 * @throws NullPointerException if the given 'manager' is null.
	 */
	public static synchronized ApplicationInfo[] getApplications(PackageManager manager) {
		Objects.requireNonNull(manager, "manager");

		if (UserPackagesUtil.applications == null) {
			List<ApplicationInfo> list = manager.getInstalledApplications(PackageManager.GET_META_DATA);

			//filter and solve
			ListIterator<ApplicationInfo> iterator = list.listIterator();
			while (iterator.hasNext()) {
				ApplicationInfo app = iterator.next();

				if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
					iterator.remove();
				else app.nonLocalizedLabel = app.loadLabel(manager);
			}

			//noinspection ComparatorCombinators
			Collections.sort(list, (a, b) -> a.nonLocalizedLabel.toString().compareTo(b.nonLocalizedLabel.toString()));

			UserPackagesUtil.applications = list.toArray(new ApplicationInfo[0]);
		}

		return UserPackagesUtil.applications;
	}

	/**
	 * Get the labels of all applications installed in the user's device.
	 *
	 * @param manager to be used.
	 * @return the labels of all the installed applications.
	 * @throws NullPointerException if the given 'manager' is null.
	 */
	public static synchronized CharSequence[] getLabels(PackageManager manager) {
		Objects.requireNonNull(manager, "manager");

		if (UserPackagesUtil.labels == null) {
			if (UserPackagesUtil.applications == null)
				getApplications(manager);

			UserPackagesUtil.labels = new CharSequence[UserPackagesUtil.applications.length];

			for (int i = 0; i < UserPackagesUtil.applications.length; i++)
				UserPackagesUtil.labels[i] = UserPackagesUtil.applications[i].nonLocalizedLabel;
		}

		return UserPackagesUtil.labels;
	}

	/**
	 * Get the packages-names of all applications installed in the user's device.
	 *
	 * @param manager to be used.
	 * @return the packages-names of all the installed applications.
	 * @throws NullPointerException if the given 'manager' is null.
	 */
	public static synchronized CharSequence[] getPackagesNames(PackageManager manager) {
		Objects.requireNonNull(manager, "manager");

		if (UserPackagesUtil.packages == null) {
			if (UserPackagesUtil.applications == null)
				getApplications(manager);

			UserPackagesUtil.packages = new CharSequence[UserPackagesUtil.applications.length];

			for (int i = 0; i < UserPackagesUtil.applications.length; i++)
				UserPackagesUtil.packages[i] = UserPackagesUtil.applications[i].packageName;
		}

		return UserPackagesUtil.packages;
	}
}
