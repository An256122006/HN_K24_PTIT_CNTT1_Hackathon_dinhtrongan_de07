package org.example.controller;

import jakarta.validation.Valid;
import org.example.model.dto.ArtWorkDto;
import org.example.model.entity.ArtWork;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/artwork")
public class ArtWorkController {
    private List<ArtWork> artWorkList = new ArrayList<>(Arrays.asList(new ArtWork(1L, "Thiếu nữ bên hoa huệ", "Tô Ngọc Vân", 1943, "1776673068000_file.jpg"), new ArtWork(2L, "Em Thúy", "Trần Văn Cẩm ", 1943, "1776673565784_file.jpg"), new ArtWork(3L, "Kết nạp Đảng Ở Điện Biên Phủ", "Nguyễn Sáng", 1956, "1776673565784_file.jpg")));

    @GetMapping("/list")
    public String Home(@RequestParam(name = "keyword", defaultValue = "") String keyword, Model model) {
        List<ArtWork> result;
        if (keyword == null || keyword.isEmpty()) {
            result = artWorkList;
        } else {
            result = artWorkList.stream().filter(artWork -> artWork.getTitle().toLowerCase().contains(keyword.toLowerCase())).toList();
        }
        if (result.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy kết quả");
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("artWorkList", result);
        return "home";
    }

    @GetMapping("/add")
    public String addArtWork(Model model) {
        model.addAttribute("artWork", new ArtWorkDto());
        return "form-add";
    }

    @PostMapping("/add")
    public String addArtWork(@Valid @ModelAttribute("artWork") ArtWorkDto artWorkDto,
                             BindingResult result,
                             @RequestParam("file") MultipartFile file) throws IOException {
        if (result.hasErrors()) {
            if (file.isEmpty()) {
                result.rejectValue("artImage", "error.artImage", "Image is required");
                return "form-add";
            }
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null || file.isEmpty() ||
                !(fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".jpg"))) {
            return "form-add";
        }
        String newFileName = System.currentTimeMillis() + "_" + fileName;
        String UploadDir = "E:\\web_service\\HN_K24_CNTT1_Hackathon_DinhTrongAn\\src\\main\\images\\";
        File dir = new File(UploadDir + newFileName);
        file.transferTo(dir);
        Long id=artWorkList.stream().mapToLong(e->e.getId()).max().orElse(0);
        artWorkList.add(new ArtWork(id+1, artWorkDto.getTitle(), artWorkDto.getArtist(), artWorkDto.getYear(), newFileName));
        return "redirect:/artwork/list";
    }
    @GetMapping("/update/{id}")
    public String updateArtWork(@PathVariable("id") Long id, Model model) {
        ArtWork artWork = artWorkList.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("artWork", artWork);
        return "form-update";
    }
    @PostMapping("/update/{id}")
    public String updateArtWork(
            @PathVariable("id") Long id,
            @ModelAttribute("artWork") ArtWork artWork,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        ArtWork old = artWorkList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (old != null) {
            old.setTitle(artWork.getTitle());
            old.setArtist(artWork.getArtist());
            old.setYear(artWork.getYear());

            if (!file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                String uploadDir = "E:\\web_service\\HN_K24_CNTT1_Hackathon_DinhTrongAn\\src\\main\\images\\";
                file.transferTo(new File(uploadDir + fileName));
                old.setArtImage(fileName);
            }
        }

        return "redirect:/artwork/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteArtWork(@PathVariable("id") Long id) {
        artWorkList.removeIf(artWork -> artWork.getId().equals(id));
        return "redirect:/artwork/list";
    }
}
