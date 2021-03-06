package com.fontys.sem3gamewebshop;

import com.fontys.sem3gamewebshop.dto.GameDTO;
import com.fontys.sem3gamewebshop.model.*;
import com.fontys.sem3gamewebshop.service.IGameService;
import com.fontys.sem3gamewebshop.service.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Sem3gamewebshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sem3gamewebshopApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    CommandLineRunner run(IUserService iUserService, IGameService igameService){
        return args -> {

            //User creation
            iUserService.saveRole(new Role(null,"ROLE_USER"));
            iUserService.saveRole(new Role(null,"ROLE_GAMEDEV"));
            iUserService.saveRole(new Role(null,"ROLE_ADMIN"));

            iUserService.saveUser(new AppUser(null, "Ivan Ovcharov", "ivan2e","ivan888@gmail.com", "12345", new ArrayList<>(), new ArrayList<>()));
            iUserService.saveUser(new AppUser(null, "Hamako Yutai", "hamako","tom123@gmail.com", "12345", new ArrayList<>(), new ArrayList<>()));
            iUserService.saveUser(new AppUser(null, "Boriz Tunai", "boriz1","boriz@gmail.com", "12345", new ArrayList<>(), new ArrayList<>()));

            AppUser ivanTest = new AppUser(null, "Ivan Test", "ivantest", "ivan@gmail.com", "12345", new ArrayList<>(), new ArrayList<>());
            iUserService.saveUser(ivanTest);
            iUserService.addRoleToUser("ivan2e", "ROLE_USER");
            iUserService.addRoleToUser("hamako", "ROLE_GAMEDEV");
            iUserService.addRoleToUser("boriz1", "ROLE_ADMIN");
            iUserService.addRoleToUser("boriz1", "ROLE_GAMEDEV");
            iUserService.addRoleToUser("boriz1", "ROLE_USER");
            iUserService.addRoleToUser("ivantest", "ROLE_ADMIN");


            igameService.saveGame(new GameDTO("Mario", 300, 25, "Adventure", ivanTest.getUsername(), GamePlayType.SinglePlayer,"A true classic, enjoy the beauty of jumping on goombas and saving the princess!"));
            igameService.saveGame(new GameDTO("Rust", 500, 40, "Survival", ivanTest.getUsername(), GamePlayType.Multiplayer, "Rust - naked, scared..and a rock in your hands?"));
            igameService.saveGame(new GameDTO("Zelda", 1000, 20, "Adventure", ivanTest.getUsername(), GamePlayType.SinglePlayer,"For sword lovers, this game will let you embark on epic quests in the land of Zelda!"));


            //Game creation
//            igameService.saveTypeGame(new TypeGame(null,"ARCADE"));
//            igameService.saveTypeGame(new TypeGame(null,"HORROR"));
//            igameService.saveTypeGame(new TypeGame(null,"SCI_FI"));
//            igameService.saveTypeGame(new TypeGame(null,"ADVENTURE"));
//            igameService.saveTypeGame(new TypeGame(null,"SURVIVAL"));

//            igameService.saveGame(new Game(null, "Mario", new ArrayList<>(), 500, 19.99, GamePlayType.SinglePlayer, ivanTest, new ArrayList<>()));
//            igameService.saveGame(new Game(null, "Zelda", new ArrayList<>(), 400, 29.99, GamePlayType.Multiplayer, ivanTest, new ArrayList<>()));
//            igameService.saveGame(new Game(null, "AdventureGame", new ArrayList<>(), 900, 39.99, GamePlayType.SinglePlayer, ivanTest, new ArrayList<>()));
//            igameService.saveGame(new Game(null, "Rust", new ArrayList<>(), 1000, 59.99, GamePlayType.Multiplayer, ivanTest, new ArrayList<>()));

//            igameService.addTypeGameToGame("Zelda","ADVENTURE");
//            igameService.addTypeGameToGame("Mario","SCI_FI");
//            igameService.addTypeGameToGame("AdventureGame","SURVIVAL");
//            igameService.addTypeGameToGame("AdventureGame","HORROR");
//            igameService.addTypeGameToGame("Rust","ADVENTURE");

        };
    }

}