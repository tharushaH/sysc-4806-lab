package org.tharushaherath;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addressbooks")
public class AddressBookRestController {

    private final AddressBookRepository repository;

    public AddressBookRestController(AddressBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AddressBook> getAllAddressBooks() {
        return (List<AddressBook>) repository.findAll();
    }

    @GetMapping("/{id}")
    public AddressBook getAddressBookById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid AddressBook ID: " + id));
    }

    @GetMapping("/{id}/buddies")
    public List<BuddyInfo> getBuddiesByAddressBookId(@PathVariable Long id) {
        AddressBook addressBook = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid AddressBook ID: " + id));
        return addressBook.getBuddies();
    }

    @PostMapping
    public AddressBook createAddressBook(@RequestBody AddressBook addressBook) {
        return repository.save(addressBook);
    }

    @PutMapping("/{id}")
    public AddressBook updateAddressBook(@PathVariable Long id, @RequestBody AddressBook updatedBook) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setBuddies(updatedBook.getBuddies());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid AddressBook ID: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteAddressBook(@PathVariable Long id) {
        repository.deleteById(id);
    }

    // Add Buddy
    @PostMapping("/{id}/buddies")
    public BuddyInfo addBuddy(@PathVariable Long id, @RequestBody BuddyInfo newBuddy) {
        AddressBook book = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid AddressBook ID: " + id));

        book.getBuddies().add(newBuddy);
        repository.save(book);
        return newBuddy;
    }

    // Delete Buddy
    @DeleteMapping("/{id}/buddies/{buddyId}")
    public void deleteBuddy(@PathVariable Long id, @PathVariable Long buddyId) {
        AddressBook book = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid AddressBook ID: " + id));

        book.getBuddies().removeIf(b -> b.getId().equals(buddyId));
        repository.save(book);
    }
}
