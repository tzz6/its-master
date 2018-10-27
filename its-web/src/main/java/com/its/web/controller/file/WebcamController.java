package com.its.web.controller.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.its.web.controller.login.BaseController;
import com.its.web.util.FileUtil;

/**
 * 拍照
 *
 */
@Controller
@RequestMapping("/webcam")
public class WebcamController extends BaseController {
	protected final transient Logger log = Logger.getLogger(WebcamController.class);

	/** 拍照 */
	@RequestMapping(value = "/photograph", method = RequestMethod.GET)
	public String index(@RequestParam(value = "id", required = false) String id, HttpServletRequest request,
			ModelMap model) {
		log.info("WebcamController-------id:" + id);
		return "/webcam/photograph";
	}

	/** 保存照片 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public void saveFile(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		log.info("WebcamController-------uploadImage----type:" + type);
		String savePath = FileUtil.getPath(request, FileUtil.IMAGE_DIR);
		// 得到文件保存的名称
		String filename = FileUtil.makeFileName("png");
		// 得到文件的保存目录
		String realSavePath = FileUtil.makePath(filename, savePath);
		savePath = realSavePath + filename;
		if (type != null && "data".equals(type)) {
			uploadBase64(request, savePath);
		} else {
			uploadBase32(request);
		}
		writeJSON("success", savePath, response);
	}

	/** base64解码 */
	public String uploadBase64(HttpServletRequest request, String savePath) {
		String image = request.getParameter("image").replace("data:image/png;base64,", "").trim();
		log.info("WebcamController-------image:" + image);
		if (image != null) {
			
//			BASE64Decoder decoder = new BASE64Decoder();
			try {
//				byte[] bytes = decoder.decodeBuffer(image);
				byte[] bytes = Base64.getDecoder().decode(image);
				for (int i = 0; i < bytes.length; ++i) {
					if (bytes[i] < 0) {// 调整异常数据
						bytes[i] += 256;
					}
				}
				OutputStream out = new FileOutputStream(savePath);
				out.write(bytes);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/** base32解码 */
	public String uploadBase32(HttpServletRequest request) {
		String width = request.getParameter("w");
		String height = request.getParameter("h");
		String pix = request.getParameter("pix");
		int w = Integer.parseInt(width);
		int h = Integer.parseInt(height);
		String savePath = "D:\\";
		try {
			BufferedImage bf = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			String[] rows = pix.split("\\|");
			for (int i = 0; i < rows.length; i++) {
				String[] col = rows[i].split(";");
				for (int j = 0; j < col.length; j++) {
					int data = Integer.parseInt(col[j], 10);
					bf.setRGB(j, i, data);
				}
			}
			File picPath = new File(savePath);
			if (!picPath.exists()) {
				picPath.mkdirs();
			}
			String fileName = UUID.randomUUID().toString() + ".jpg";
			savePath += fileName;
			ImageIO.write(bf, "jpg", new File(savePath));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * RCP客户端上传拍照图片
	 */
	@RequestMapping(value = "/phototaking/uploadImage", method = RequestMethod.POST)
	public void uploadImage(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ext = request.getParameter("ext");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile file = entity.getValue();
				if (!file.isEmpty()) {
					String savePath = FileUtil.getPath(request, FileUtil.IMAGE_DIR);
					// 得到文件保存的名称
					String filename = FileUtil.makeFileName(ext);
					// 得到文件的保存目录
					String realSavePath = FileUtil.makePath(filename, savePath);
					savePath = realSavePath + filename;
					file.transferTo(new File(savePath));
					responseResult("<Response><success>true</success><reason>true</reason></Response>");
				}
			}
		} catch (Exception e) {
			log.error("WsmController@uploadImage", e);
			responseResult("<Response><success>false</success><reason>系统异常！原因：" + e.getMessage() + "</reason></Response>");
		}
	}
}
