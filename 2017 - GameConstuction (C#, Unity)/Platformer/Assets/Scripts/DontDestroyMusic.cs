using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DontDestroyMusic : MonoBehaviour {

	static bool created = false;

	public void Awake () {
		if (!created) {
			DontDestroyOnLoad (this.gameObject);
		} else {
			Destroy (this.gameObject);
		}

	}

	public void OnLevelWasLoaded(int level) {
		if (level == 0) {
			Destroy (this.gameObject);
		}
	}


}
