package com.tedu.straw.portal.controller;

import com.tedu.straw.portal.service.IUserService;
import com.tedu.straw.portal.vo.R;
import com.tedu.straw.portal.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Slf4j
public class SystemController {

    @Autowired
    IUserService userService;

    @Value("${straw.resource.path}")
    private File resourcePath;

    @Value("${straw.resource.host}")
    private String resourceHost;

    @GetMapping("/login.html")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/register.html")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public R registerStudent(@Validated RegisterVo registerVo,
                             BindingResult validaResult) {
        log.debug("得到登陸表單:{}", registerVo);
        if (validaResult.hasErrors()) {
            String error = validaResult.getFieldError().getDefaultMessage();
            log.info(error);
            return R.unprocessableEntity(error);
        }
        if (!registerVo.getPassword().equals(registerVo.getConfirm())) {
            return R.unprocessableEntity("確認密碼不一致!!");
        }
        userService.registerStudent(registerVo);
        return R.created("註冊學生測試");
    }

    @PostMapping("/upload/file")
    public R uploadFile(MultipartFile imageFile) throws IOException {
        String path = "G:/straw/upload";
        String filename = imageFile.getOriginalFilename();

        File folder = new File(path);
        folder.mkdirs();
        log.debug("上傳文件夾{}", folder.getAbsolutePath());

        log.debug("上傳文件{}", filename);
        File file = new File(folder, filename);
        log.debug("保存到:{}", file.getAbsolutePath());

        imageFile.transferTo(file);

        return R.ok("上傳成功");
    }

    @PostMapping("/upload/image")
    public R uploadImage(MultipartFile imageFile) throws IOException {
        String path = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now());
        File folder = new File(resourcePath, path);
        folder.mkdirs();
        log.debug("存儲文件夾{}", folder.getAbsolutePath());

        String filename = imageFile.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf('.'));
        log.debug("擴展名:{}", ext);

        String name = UUID.randomUUID().toString() + ext;
        File file = new File(folder, name);
        log.debug("保存到:{}", file.getAbsolutePath());

        imageFile.transferTo(file);

        String url = resourceHost + "/" + path + "/" + name;
        log.debug("Image URL:" + url);

        return R.ok(url);
    }
}

