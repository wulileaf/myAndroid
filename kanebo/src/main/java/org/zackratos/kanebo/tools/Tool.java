package org.zackratos.kanebo.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

public class Tool {

	
	public static boolean isInstalled(String packageName, Context context) {
		PackageManager manager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
		if (installedPackages != null) {
			for (PackageInfo info : installedPackages) {
				if (info.packageName.equals(packageName))
					return true;
			}
		}
		return false;
	}
	
	public static Resources getResources(Context context) {
		if (context != null) {
			return context.getResources();
		}
		return null;
	}

	public static DisplayMetrics getDisplayMetrics(Context context) {
		if (context != null) {
			return getResources(context).getDisplayMetrics();
		}
		return null;
	}

	public static float getDensity(Context context) {
		if (context != null) {
			return getDisplayMetrics(context).density;
		}
		return 0;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = getDensity(context);
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = getDensity(context);
		return (int) (pxValue / scale + 0.5f);
	}

	public static void saveFile(byte[] data) {
		try {
			String base64 = Base64.encodeToString(data, Base64.DEFAULT);
			File file = new File("/sdcard/sfa//test.txt");

			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(base64.getBytes());
			fout.close();
		} catch (Exception e) {
			// TODO: handle exception
			// String sssString = e.getMessage();
		}
	}

	public static String UUID() {
		final UUID uuid = UUID.randomUUID();
		final String uniqueId = uuid.toString();
		return uniqueId;
	}





	//获取手机的唯一标识
	public String getPhoneSign(Context context) {
		StringBuilder deviceId = new StringBuilder();
		// 渠道标志
		deviceId.append("a");
		try {
			//IMEI（imei）
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = tm.getDeviceId();
			if (!TextUtils.isEmpty(imei)) {
				deviceId.append("imei");
				deviceId.append(imei);
				return deviceId.toString();
			}
			//序列号（sn）
			String sn = tm.getSimSerialNumber();
			if (!TextUtils.isEmpty(sn)) {
				deviceId.append("sn");
				deviceId.append(sn);
				return deviceId.toString();
			}
			//如果上面都没有， 则生成一个id：随机码
			String uuid = getUUID(context);
			if (!TextUtils.isEmpty(uuid)) {
				deviceId.append("id");
				deviceId.append(uuid);
				return deviceId.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			deviceId.append("id").append(getUUID(context));
		}
		return deviceId.toString();
	}

	/**
	 * 得到全局唯一UUID
	 */
	private String uuid;

	public String getUUID(Context context) {
		SharedPreferences mShare = context.getSharedPreferences("uuid", context.MODE_PRIVATE);
		if (mShare != null) {
			uuid = mShare.getString("uuid", "");
		}
		if (TextUtils.isEmpty(uuid)) {
			uuid = UUID.randomUUID().toString();
			mShare.edit().putString("uuid", uuid).commit();
		}
		return uuid;
	}
}
