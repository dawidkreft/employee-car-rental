package pl.kreft.thesis.ecr.centralsystem.user.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static pl.kreft.thesis.ecr.centralsystem.dbtestcleaner.DbCleaner.clearDatabase;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getBoss;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getEmployee;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getSecondEmployee;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        try {
            clearDatabase();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initialize db exception");
        }
    }

    @Test
    void shouldSaveAndFindById() {
        User testUser = getEmployee();
        User testUser1 = getSecondEmployee();

        userRepository.save(testUser);
        userRepository.save(testUser1);
        Optional<User> user = userRepository.findById(testUser.getId());
        List<User> allUsers = userRepository.findAll();

        assertEquals(testUser.getId(), user.get().getId());
        assertEquals(2, allUsers.size());
    }

    @Test
    void shouldFindEmployeesByBoosId() {
        User testUser = userRepository.save(getEmployee());
        User testUser1 = userRepository.save(getSecondEmployee());
        User boos = userRepository.save(getBoss());
        testUser.setBossId(boos.getId());
        testUser1.setBossId(boos.getId());
        userRepository.save(testUser);
        userRepository.save(testUser1);

        List<User> users = userRepository.findAllByBossIdAndRemovedIsFalseAndIsActiveTrue(
                boos.getId());
        List<User> all = userRepository.findAll();

        assertEquals(2, users.size());
        assertEquals(3, all.size());
    }
}