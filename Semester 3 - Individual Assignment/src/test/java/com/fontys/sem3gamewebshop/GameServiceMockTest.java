package com.fontys.sem3gamewebshop;
import com.fontys.sem3gamewebshop.converters.GameConverter;
import com.fontys.sem3gamewebshop.dal.IAppUserDAL;
import com.fontys.sem3gamewebshop.dal.IGameDAL;
import com.fontys.sem3gamewebshop.dto.GameDTO;
import com.fontys.sem3gamewebshop.model.AppUser;
import com.fontys.sem3gamewebshop.model.Game;
import com.fontys.sem3gamewebshop.model.GamePlayType;
import com.fontys.sem3gamewebshop.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class GameServiceMockTest {
    @Mock
    IGameDAL gameDAL;
    @Mock
    IAppUserDAL iAppUserDAL;
    @Mock
    GameConverter gameConverter;

    @BeforeEach
    public void setUp() {
        AppUser ivanTest = new AppUser(15l, "Ivan Test", "ivantest", "ivan@gmail.com", "12345", new ArrayList<>(), new ArrayList<>());
        List<Game> games = List.of(
                new Game(null, "Mario", "Adventure", 500, 19.99, GamePlayType.SinglePlayer, "A true classic, enjoy the beauty of jumping on goombas and saving the princess!", ivanTest, new ArrayList<>()),
                new Game(null, "Zelda", "Adventure", 400, 29.99, GamePlayType.Multiplayer, "For sword lovers, this game will let you embark on epic quests in the land of Zelda!", ivanTest, new ArrayList<>()),
                new Game(null, "AdventureGame", "Action", 900, 39.99, GamePlayType.SinglePlayer, "The title says it all!", ivanTest, new ArrayList<>()),
                new Game(null, "Rust", "Survival", 1000, 59.99, GamePlayType.Multiplayer, "Rust - naked, scared..and a rock in your hands?", ivanTest, new ArrayList<>())


        );

        Game game = new Game(null, "Mario", "Adventure", 500, 19.99, GamePlayType.SinglePlayer, "A true classic, enjoy the beauty of jumping on goombas and saving the princess!", ivanTest, new ArrayList<>());
        Game game2 = new Game(null, "AdventureGame", "Action", 900, 39.99, GamePlayType.SinglePlayer, "The title says it all!", ivanTest, new ArrayList<>());
        List<Game> testGames = new ArrayList<>();
        testGames.add(game);
        List<Game> testGames2 = new ArrayList<>();
        testGames2.add(game2);
        when(gameDAL.getGames()).thenReturn(games);
        when(gameDAL.getGamesByPlayType(GamePlayType.SinglePlayer)).thenReturn(testGames);
        when(gameDAL.getGamesByPlayType(GamePlayType.Multiplayer)).thenReturn(testGames2);
        when(gameDAL.GetGamesByUser(15l)).thenReturn(testGames);
    }

    @Test
    public void getAllGamesTest() {
        GameService gameService = new GameService(gameDAL, iAppUserDAL, gameConverter);
        List<Game> games = gameService.getGames();
        Assertions.assertEquals(games.get(0).getGameName(), "Mario");
        Assertions.assertEquals(games.get(1).getGameName(), "Zelda");
        Assertions.assertEquals(games.get(2).getGameName(), "AdventureGame");
        Assertions.assertEquals(games.get(3).getGameName(), "Rust");
    }


    @Test
    public void changeGamePlayType() {
        GameService gameService = new GameService(gameDAL, iAppUserDAL, gameConverter);
        List<Game> games = gameService.getGames();
        Game game = games.get(0);
        game.setGamePlayType(GamePlayType.Multiplayer);
        Assertions.assertEquals(game.getGamePlayType(), GamePlayType.Multiplayer);
    }

    //
//
    @Test
    public void saveGame() {
        GameService gameService = new GameService(gameDAL, iAppUserDAL, gameConverter);
        AppUser ivanTest = new AppUser(null, "Ivan Test", "ivantest", "ivan@gmail.com", "12345", new ArrayList<>(), new ArrayList<>());
        GameDTO game = new GameDTO("Mario", 300, 25, "Adventure", ivanTest.getUsername(), GamePlayType.Multiplayer, new ArrayList<>(), "A true classic, enjoy the beauty of jumping on goombas and saving the princess!");
        gameService.saveGame(game);
        Assertions.assertEquals(gameService.GetGamesByUser(15l).get(0).getGameName(), game.getGameName());

    }

    @Test
    public void getGameByGameType() {
        GameService gameService = new GameService(gameDAL, iAppUserDAL, gameConverter);
        AppUser ivanTest = new AppUser(null, "Ivan Test", "ivantest", "ivan@gmail.com", "12345", new ArrayList<>(), new ArrayList<>());
        GameDTO game = new GameDTO("Mario", 300, 25, "Adventure", ivanTest.getUsername(), GamePlayType.Multiplayer, new ArrayList<>(), "A true classic, enjoy the beauty of jumping on goombas and saving the princess!");
        gameService.saveGame(game);
        gameService.GetGamesByUser(15l).get(0).setGameType("Test");
        Assertions.assertEquals(gameService.GetGamesByUser(15l).get(0).getGameType(), "Test");
    }

    @Test
    public void changeGameUser() {
        GameService gameService = new GameService(gameDAL, iAppUserDAL, gameConverter);
        AppUser ivanTest = new AppUser(15l, "Ivan Test", "ivantest", "ivan@gmail.com", "12345", new ArrayList<>(), new ArrayList<>());
        GameDTO game = new GameDTO("Mario", 300, 25, "Adventure", ivanTest.getUsername(), GamePlayType.Multiplayer, new ArrayList<>(), "A true classic, enjoy the beauty of jumping on goombas and saving the princess!");
        gameService.saveGame(game);
        AppUser changeTestUser = new AppUser(null, "NewUser", "NewUser", "ivan@gmail.com", "12345", new ArrayList<>(), new ArrayList<>());
        gameService.GetGamesByUser(15l).get(0).setAppUser(changeTestUser);
        Assertions.assertEquals( gameService.GetGamesByUser(15l).get(0).getAppUser().getUsername(), changeTestUser.getUsername());
    }
}