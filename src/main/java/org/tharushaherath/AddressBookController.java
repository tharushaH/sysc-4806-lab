package org.tharushaherath;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/addressbooks")
public class AddressBookController {

    private final AddressBookRepository repository;

    public AddressBookController(AddressBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("addressbooks", repository.findAll());
        return "addressbooks";
    }

    @GetMapping("/{id}")
    public String buddies(@PathVariable Long id, Model model) {
        AddressBook addressBook = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid AddressBook ID: " + id));
        model.addAttribute("addressbook", addressBook);
        model.addAttribute("buddies", addressBook.getBuddies());
        return "buddies";
    }


}
