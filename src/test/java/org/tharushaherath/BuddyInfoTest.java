package org.tharushaherath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BuddyInfoTest {

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    @Test
    void getName() {
        BuddyInfo buddy = new BuddyInfo("Alice", "123");
        assertEquals("Alice", buddy.getName());
    }

    @Test
    void setName() {
        BuddyInfo buddy = new BuddyInfo("Alice", "123");
        buddy.setName("Bob");
        assertEquals("Bob", buddy.getName());
    }

    @Test
    void getPhoneNumber() {
        BuddyInfo buddy = new BuddyInfo("Alice", "123");
        assertEquals("123", buddy.getPhoneNumber());
    }

    @Test
    void setPhoneNumber() {
        BuddyInfo buddy = new BuddyInfo("Alice", "123");
        buddy.setPhoneNumber("456");
        assertEquals("456", buddy.getPhoneNumber());
    }

    @Test
    void testToString() {
        BuddyInfo buddy = new BuddyInfo("Alice", "123");
        String s = buddy.toString();
        assertTrue(s.contains("Alice"));
        assertTrue(s.contains("123"));
    }

    @Test
    void persistAndRetrieveBuddyInfo() {
        buddyInfoRepository.deleteAll();
        BuddyInfo buddy = new BuddyInfo("Alice", "123");

        // persist
        buddyInfoRepository.save(buddy);

        // retrieve
        List<BuddyInfo> results = (List<BuddyInfo>) buddyInfoRepository.findAll();

        assertThat(results).hasSize(1);
        BuddyInfo result = results.get(0);
        assertThat(result.getName()).isEqualTo("Alice");
        assertThat(result.getPhoneNumber()).isEqualTo("123");
    }
}
