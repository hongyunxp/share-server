package edu.tjpu.share.biz.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import edu.tjpu.share.biz.IUpdate;
import edu.tjpu.share.po.FileForDownload;

public class UpdateImpl implements IUpdate {

	@Override
	public FileForDownload doCheckUpdate(int localVersion) {
		URL base = this.getClass().getResource("");
		String filePath = null;
		try {
			filePath = new File(base.getFile(), "../../../../../../../update/")
					.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-----------path:" + filePath + File.separator
				+ "update.properties");
		// Properties prop = new Properties();
		// try {
		// prop.load(Config.class.getResourceAsStream(filePath
		// +File.separator+ "update.properties"));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		File fileToRead = new File(filePath + File.separator
				+ "update.properties");
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String serverVersiontmp = null;
		String fileNametmp = null;
		try {
			fis = new FileInputStream(fileToRead);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			serverVersiontmp = br.readLine();
			fileNametmp = br.readLine();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		if (serverVersiontmp != null && fileNametmp != null) {
			serverVersiontmp = serverVersiontmp.substring(
					serverVersiontmp.indexOf("=")+1, serverVersiontmp.length());
			fileNametmp = fileNametmp.substring(fileNametmp.indexOf("=")+1,
					fileNametmp.length());

			int serverVersion = Integer.parseInt(serverVersiontmp);
			System.out.println("--------serverVersion:" + serverVersion);
			String fileName = fileNametmp;
			System.out.println("--------fileName:" + fileName);
			if (localVersion < serverVersion) {
				FileForDownload download = new FileForDownload();
				StringBuilder sb = new StringBuilder(filePath + File.separator +fileName);
				String path = sb
						.subSequence(
								sb.indexOf(File.separator + "ShareServer"),
								sb.length()).toString();
				System.out.println("--------Furl:" + path);
				download.setFurl(path);
				download.setFname(fileName);
				return download;
			}
		}
		return null;
	}

}
