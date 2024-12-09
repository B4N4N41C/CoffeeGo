package ru.mochalin.coffeego.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mochalin.coffeego.Model.Product;
import ru.mochalin.coffeego.Repository.ProductRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    public static String PATH_IN_STATIC = "/img/products/";
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static" + PATH_IN_STATIC;

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "edit-product";
    }

    @PostMapping("/edit")
    public String edit(Model model, @ModelAttribute("product") Product product, @RequestParam("imageProduct") MultipartFile image) throws IOException {
        product.setImage(PATH_IN_STATIC + image.getOriginalFilename());
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, image.getOriginalFilename());
        Files.write(fileNameAndPath, image.getBytes());
        productRepository.save(product);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "create-product";
    }

    // TODO: Реализовать загрузку фото массивом байт потому, потому что позсле загрузки новой фотографии в static необходимо перезагружать сервер
}
