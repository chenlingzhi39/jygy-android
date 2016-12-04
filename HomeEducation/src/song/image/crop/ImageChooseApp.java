package song.image.crop;

import com.endeavour.jygy.app.ImageLoadApp;

import java.io.File;

public class ImageChooseApp extends ImageLoadApp {
	private static ImageChooseApp instance;

	private File singleChooseFile;

	public static ImageChooseApp getInstance() {
		return instance;
	}

	public void onCreate() {
		super.onCreate();
		instance = this;

	}

	public File getSingleChooseFile() {
		return singleChooseFile;
	}

	public void setSingleChooseFile(File singleChooseFile) {
		if (singleChooseFile == null) {// 释放并删除已有图片
			if (this.singleChooseFile != null && this.singleChooseFile.exists()) {
				this.singleChooseFile.delete();
			}
		} else {
			this.singleChooseFile = singleChooseFile;
		}
	}
}
