package tools.com.vera.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by huasheng on 16/6/27.
 */
public class FileUtils {

	public static String getTempFolderPath() {
		File tAppDir = new File(Environment.getExternalStorageDirectory() + File.separator + "restphone_temp");
		if (!tAppDir.exists()) tAppDir.mkdirs();
		return tAppDir.getAbsolutePath();
	}

	/**
	 * 得到热补丁打补丁文件.
	 *
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static File getHotFixPatch(Context context, String path, String fileName) {
		File fileDir = new File(context.getApplicationContext().getCacheDir().getAbsolutePath() + File.separator + path);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		File patchFile = new File(fileDir, fileName);
		return patchFile;
	}

	/**
	 * 得到热补丁打补丁文件路径.
	 *
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String getHotFixPatchPath(Context context, String path, String fileName) {
		String patchPath = context.getApplicationContext().getCacheDir().getAbsolutePath() +
				File.separator + path + File.separator + fileName;
		return patchPath;
	}

	/**
	 * 查找补丁是否存在
	 * @param context
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static boolean isExistsPatch(Context context, String path, String fileName){
		File file = getHotFixPatch(context, path, fileName);
		return file.exists();
	}

	/**
	 * 得到热补丁打补丁文件.
	 *
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static File getHotFixPatch(String path, String fileName) {
		File fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + path);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		File patchFile = new File(fileDir, fileName);
		return patchFile;
	}

	/**
	 * 获取单个文件的MD5值！
	 *
	 * @param file
	 * @return
	 */

	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

	/**
	 * 根据uri获取文件path
	 *
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getFilePathFromContentUri(Context context, Uri uri) {
		String photoPath = "";
		if (context == null || uri == null) {
			return photoPath;
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
			String docId = DocumentsContract.getDocumentId(uri);
			if (isExternalStorageDocument(uri)) {
				String[] split = docId.split(":");
				if (split.length >= 2) {
					String type = split[0];
					if ("primary".equalsIgnoreCase(type)) {
						photoPath = Environment.getExternalStorageDirectory() + "/" + split[1];
					}
				}
			}
			else if (isDownloadsDocument(uri)) {
				Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
				photoPath = getDataColumn(context, contentUri, null, null);
			}
			else if (isMediaDocument(uri)) {
				String[] split = docId.split(":");
				if (split.length >= 2) {
					String type = split[0];
					Uri contentUris = null;
					if ("image".equals(type)) {
						contentUris = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
					}
					else if ("video".equals(type)) {
						contentUris = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
					}
					else if ("audio".equals(type)) {
						contentUris = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
					}
					String selection = MediaStore.Images.Media._ID + "=?";
					String[] selectionArgs = new String[]{split[1]};
					photoPath = getDataColumn(context, contentUris, selection, selectionArgs);
				}
			}
		}
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			photoPath = uri.getPath();
		}
		else {
			photoPath = getDataColumn(context, uri, null, null);
		}

		return photoPath;
	}

	private static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	private static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	private static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
		Cursor cursor = null;
		String column = MediaStore.Images.Media.DATA;
		String[] projection = {column};
		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null && !cursor.isClosed())
				cursor.close();
		}
		return null;
	}

	public static void saveBitmapToFile(String aFileName, Bitmap aBmp) throws IOException {
		if (aBmp == null || TextUtils.isEmpty(aFileName)) throw new IOException();

		File tFile = new File(aFileName);
		FileOutputStream tFos = new FileOutputStream(tFile);
		aBmp.compress(Bitmap.CompressFormat.JPEG, 100, tFos);
		tFos.flush();
		tFos.close();
	}

	public static String readAssestFileText(Context aContext, String path) {
		try {
			InputStream is = aContext.getAssets().open(path);
			int size = is.available();

			// Read the entire asset into a local byte buffer.
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();

			// Convert the buffer into a string.
			return new String(buffer);

			// Finally stick the string into the text view.
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
