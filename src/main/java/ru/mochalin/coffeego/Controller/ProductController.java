package ru.mochalin.coffeego.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mochalin.coffeego.Model.Product;
import ru.mochalin.coffeego.Repository.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public static String PATH_IN_STATIC = "/img/products/";
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static" + PATH_IN_STATIC;

    @GetMapping("/edit/{id}")
    public String editPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "edit-product";
    }

    @PostMapping("/edit")
    public String edit(Model model, @ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file)
            throws IOException {
        saveImg(product, file);

        productRepository.save(product);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("product", new Product());
        return "create-product";
    }

    @PostMapping("/create")
    public String create(Model model, @ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file)
            throws IOException {
        saveImg(product, file);

        productRepository.save(product);
        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @ModelAttribute("product") Product product, @PathVariable("id") Long id) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "detail-product";
    }

    private void saveImg(Product product, MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();

        file.transferTo(new File(uploadPath + resultFilename));
        product.setImage(resultFilename);
    }
}
