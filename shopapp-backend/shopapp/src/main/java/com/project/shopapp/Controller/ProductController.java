package com.project.shopapp.Controller;
import com.github.javafaker.Faker;
import com.project.shopapp.Dto.ProductDto;
import com.project.shopapp.Dto.ProductImageDto;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ProductListResponse;
import com.project.shopapp.responses.ProductResponse;
import com.project.shopapp.services.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProduct(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page, limit,
                Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        //lấy ra tổng số trang
        int totalPage = productPage.getTotalPages();
        //ấy ra list các danh sách sản phẩm
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                        .products(products)
                        .totalPages(totalPage)
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable ("id") Long productId){
        try {
            Product existingProduct = productService.getProductById(productId);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping( "")
    public ResponseEntity<?> createProduct(
            @Valid
            @RequestBody ProductDto productDto,
            BindingResult result
            ){

        try {
            if (result.hasErrors()){
                List<String> errorMessage =  result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }

            Product newProduct = productService.createProduct(productDto);
            return ResponseEntity.ok(newProduct);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files
    ){

        try {
            Product existingProduct = productService.getProductById(productId);
            if  (files.size() >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT){
                return ResponseEntity.badRequest().body("You can only upload maximum 5 images");
            }
            files = files == null? new ArrayList<MultipartFile>():files;
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files){
                if (file.getSize() == 0){
                    continue;
                }
                //kiểm tra kích thước file ảnh
                if (file.getSize() > 10 *1024 * 1024){
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("file is too large");
                }

//                lưu file cập nhập vào thumbnail
                String filename =storeFile(file);
                ProductImage productImage = productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDto.builder()
                                .imageUrl(filename)
                                .build()
                );
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    private String storeFile(MultipartFile file) throws IOException{
        if (!isImageFile(file) || file.getOriginalFilename() == null){
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//        thêm UUID vào trước filename để đảm bảo file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
//        đường dẫn thư mục
        Path uploadDir = Paths.get("uploads");
//        kiểm tra và tạo thư mục nếu nó ko tồn tại
        if (!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
//        đường dẫn đầy đủ đến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
//        sao chep file vào thư mục des
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    private boolean isImageFile(MultipartFile file){
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(String.format("Product delete with id= %d Successfully",id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    //@PostMapping("/generateFakerProducts")
    public ResponseEntity<String> generateFakerProducts(){
         Faker faker = new Faker();
        for (int i = 0; i < 1000; i++){
            String productName = faker.commerce().productName();
            if (productService.existsByName(productName)){
                continue;
            }
            ProductDto productDto = ProductDto.builder()
                    .name(productName)
                    .price((float) faker.number().numberBetween(10, 90000000))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .categoryId((long) faker.number().numberBetween(3,6))
                    .build();
            try {
                productService.createProduct(productDto);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("fake products created successfully");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable long id,
            @RequestBody ProductDto productDto
    ){
        try {
            Product updateProduct = productService.updateProduct(id, productDto);
            return ResponseEntity.ok(updateProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
