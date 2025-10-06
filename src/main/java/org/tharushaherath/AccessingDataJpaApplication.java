package org.tharushaherath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessingDataJpaApplication {

    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AddressBookRepository addressBookRepository, BuddyInfoRepository buddyRepository) {
        return (args) -> {
            AddressBook book1 = new AddressBook();
            book1.addBuddy(new BuddyInfo("Alice", "123-456-7890", "123 Maple Street, Springfield"));
            book1.addBuddy(new BuddyInfo("Bob", "987-654-3210", "456 Oak Avenue, Riverside"));
            addressBookRepository.save(book1);

            AddressBook book2 = new AddressBook();
            book2.addBuddy(new BuddyInfo("Charlie", "555-123-4567", "789 Pine Road, Lakeside"));
            book2.addBuddy(new BuddyInfo("Diana", "222-333-4444", "321 Elm Boulevard, Hilltown"));
            addressBookRepository.save(book2);

            AddressBook book3 = new AddressBook();
            book3.addBuddy(new BuddyInfo("Eve", "111-222-3333", "654 Cedar Lane, Mountainview"));
            book3.addBuddy(new BuddyInfo("Frank", "444-555-6666", "987 Birch Court, Seaside"));
            book3.addBuddy(new BuddyInfo("Grace", "777-888-9999", "147 Willow Drive, Plainfield"));
            addressBookRepository.save(book3);

            log.info("All AddressBooks:");
            addressBookRepository.findAll().forEach(addressBook -> {
                log.info("AddressBook id=" + addressBook.getId());
                addressBook.getBuddies().forEach(buddy -> log.info("  " + buddy));
            });

        };
    }
}