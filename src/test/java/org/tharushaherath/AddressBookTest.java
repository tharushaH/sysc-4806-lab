package org.tharushaherath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest  // boots only JPA components, good for repository tests
class AddressBookTest {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    void addBuddy() {
        AddressBook book = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("Alice", "123");

        book.addBuddy(buddy);

        assertTrue(book.getBuddies().contains(buddy));
        assertEquals(1, book.getBuddies().size());
    }

    @Test
    void removeBuddy() {
        AddressBook book = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("Bob", "987");

        book.addBuddy(buddy);
        book.removeBuddy(buddy);

        assertFalse(book.getBuddies().contains(buddy));
        assertEquals(0, book.getBuddies().size());
    }

    @Test
    void printBuddies() {
        AddressBook book = new AddressBook();
        BuddyInfo buddy1 = new BuddyInfo("Alice", "123");
        BuddyInfo buddy2 = new BuddyInfo("Bob", "987");

        book.addBuddy(buddy1);
        book.addBuddy(buddy2);

        List<BuddyInfo> buddies = book.getBuddies();
        assertEquals(2, buddies.size());
        assertEquals("Alice", buddies.get(0).getName());
        assertEquals("Bob", buddies.get(1).getName());
    }

    @Test
    void getBuddies() {
        AddressBook book = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("Charlie", "111");

        book.addBuddy(buddy);

        List<BuddyInfo> buddies = book.getBuddies();
        assertNotNull(buddies);
        assertEquals(1, buddies.size());
        assertEquals("Charlie", buddies.get(0).getName());
    }

    @Test
    void persistAddressBookWithBuddies() {
        addressBookRepository.deleteAll();

        BuddyInfo buddy1 = new BuddyInfo("Alice", "123");
        BuddyInfo buddy2 = new BuddyInfo("Bob", "456");

        AddressBook book = new AddressBook();
        book.addBuddy(buddy1);
        book.addBuddy(buddy2);

        addressBookRepository.save(book);

        List<AddressBook> all = (List<AddressBook>) addressBookRepository.findAll();
        assertThat(all).hasSize(1);
    }
}
