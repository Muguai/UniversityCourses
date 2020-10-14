using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using DG.Tweening;
using UnityEngine.SceneManagement;

public class GameController : MonoBehaviour {

	public static GameController gameControllerInstance;
	public Text coinText;
    public Text mobText;
	public Slider healthSlider;
	public float playerHealth;

	private Quaternion originalCameraRotation;

	[HideInInspector]
	public int coins;
    public int mobs;

	// Use this for initialization
	void Start () {
		gameControllerInstance = this;
		coins = 0;
		originalCameraRotation = Camera.main.transform.rotation;
	}
	
	// Update is called once per frame
	void Update () {
		coinText.text = "" + coins;
        mobText.text = "" + mobs;
		healthSlider.value = playerHealth;
	}

	public void ScreenShake() {
		Camera.main.DOShakeRotation (0.2f, 4, 40, 90);
		Invoke ("ResetCameraRotation", 0.2f);
	}

	private void ResetCameraRotation(){
		Camera.main.transform.rotation = originalCameraRotation;
	}

	public void LoadLevel (int levelToLoad){
		int previousCoins = PlayerPrefs.GetInt ("coins");
		previousCoins += coins;
		PlayerPrefs.SetInt ("coins", previousCoins);
		SceneManager.LoadScene (levelToLoad);
	}
}
