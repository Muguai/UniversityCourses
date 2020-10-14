using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Lift : MonoBehaviour {

	private void OnCollisionEnter2D(Collision2D other){
		if (other.collider.CompareTag ("Player")) {
			other.transform.parent = transform;
			//other.transform.parent.localScale = new Vector3 (1,1,1);
		}
	}

	private void OnCollisionExit2D(Collision2D other){
		if (other.collider.CompareTag ("Player")) {
			other.transform.parent = null;
		}
	}
}
