package lsafer.edgeseek.data;

import cufy.beans.AbstractBean;
import cufyx.perference.MapDataStore;
import lsafer.edgeseek.BuildConfig;

/**
 * A bean that contains the data of this application.
 *
 * @author lsafer
 * @version 0.1.5
 * @since 10-Jun-2020
 */
public class AboutData extends AbstractBean {
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String VERSION = "version";
	/**
	 * Just a string represents the key of a field in this.
	 */
	final public static String VERSION_CODE = "versionCode";

	/**
	 * The data-store of this bean.
	 */
	final public MapDataStore store = new MapDataStore(this);

	/**
	 * The version of this application.
	 */
	@Property
	public String version = BuildConfig.VERSION_NAME;
	/**
	 * The version code of this application.
	 */
	@Property
	public int versionCode = BuildConfig.VERSION_CODE;
}
