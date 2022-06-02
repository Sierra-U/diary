package com.example.diary;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller //コントローラークラスにはとりえあず@Controllerを付ける
@RequestMapping("diary") //URLマッピングができる(http://localhost:8080/diary)＆GET・POST関係なく受け付ける
public class DiaryController {
	@Autowired
	DiaryRepository diaryRepository;
	
	//日記一覧情報の取得
	@GetMapping("summary") //URLマッピングを設定(-:-8080/diary/summary)するするがHTTPリクエストのGETだけ受け付けてPOSTは受け付けない
	public String summary(Model model, NewDiaryForm newDiaryFrom){
		Iterable <Diary> diarys = diaryRepository.findAll();//diaryテーブルから日記の一覧情報を取得す
		model.addAttribute("list", diarys);
		return "summary";
	}
	
	//指定されたidの日記を削除する
	@PostMapping("delete")
	public String delete(@RequestParam Integer id) {
		diaryRepository.deleteById(id);
		return "redirect:/diary/summary";
	}
	
	//@Validを@Validatedに変更しても問題なく動作する
	//日記の新規登録
	@PostMapping("add")
	public String add(Model model, @Valid NewDiaryForm newDiaryForm, BindingResult bindingResult){
		//ヴァリデーションエラーがあるかどうかチェックしてエラーがあるなら新規登録処理せず、一覧表示メソッドを呼び出す
		if (bindingResult.hasErrors()){
			return summary(model, newDiaryForm);
		}
		Diary diary = new Diary(newDiaryForm.getNewdiary(), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		diaryRepository.save(diary);
		return "redirect:/diary/summary";
	}
	
	
	//編集画面を表示する
	@GetMapping("edit")
	String edit(Model model, EditDiaryForm editDiaryForm) {
		Diary diary = diaryRepository.findById(editDiaryForm.getId()).get();
		model.addAttribute("editdiary", diary);
		return "edit";
	}
	
	
	//日記を更新する
	@PostMapping("update")
	String update(Model model, @Valid EditDiaryForm editDiaryForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return edit(model, editDiaryForm);
		}
		Diary diary = new Diary(editDiaryForm.getId(), editDiaryForm.getUpdateddiary(),
				LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		diaryRepository.save(diary);
		return "redirect:/diary/summary";
	}
}
