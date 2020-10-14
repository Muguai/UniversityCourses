using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Blockade : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {

        
        if (GameController.gameControllerInstance.mobs == 5)
        {
            gameObject.SetActive(false);
        }


    }
}
