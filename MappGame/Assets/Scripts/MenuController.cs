using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MenuController : MonoBehaviour {

public void StartGame()
    {
        SceneManager.LoadScene(1);
    }

    public void ShowCredits()
    {
        SceneManager.LoadScene(2);
    }

    public void ShowMainMenu()
    {
        SceneManager.LoadScene(0);
    }

public void QuitGame()
    {
        Application.Quit();
    }
}
