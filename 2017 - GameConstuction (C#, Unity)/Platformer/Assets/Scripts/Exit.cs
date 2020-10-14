using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Exit : MonoBehaviour {


    public int levelToLoad;

	void OnTriggerEnter2D(Collider2D other){
		if (other.CompareTag ("Player")) {
			GameController.gameControllerInstance.LoadLevel (levelToLoad);
		}
	}
}
