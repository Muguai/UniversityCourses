using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PauseMenu : MonoBehaviour

{   [HideInInspector]
    public bool paused = false;

    void PauseGame()
    {
        if(Input.GetKeyDown(KeyCode.Escape))
        paused = switchPause();
    }

    void PauseBox()
    {
        if (paused)
        {
            GUI.BeginGroup(new Rect(Screen.width / 2 - 70, Screen.height / 2 - 70, 140, 140));
            GUI.Box(new Rect(0, 0, 140, 140), "Pause");
            GUILayout.Label(" \n \n");
            if (GUILayout.Button("Continue"))
                paused = switchPause();
            if (GUILayout.Button("Main Menu"))
            {
                paused = switchPause();
                SceneManager.LoadScene(0);
            }

            GUI.EndGroup();
        }

    }

    bool switchPause()
    {
        if (Time.timeScale == 0f)
        {
            Time.timeScale = 1f;
            return (false);
        }
        else
        {
            Time.timeScale = 0f;
            return (true);
        }
    }
}
