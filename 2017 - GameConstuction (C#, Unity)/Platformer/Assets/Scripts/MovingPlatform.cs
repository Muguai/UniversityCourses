using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MovingPlatform : MonoBehaviour {

	public Transform left, right;
	public float moveSpeed = 1.0f;

	private bool movingRight;

	void Start () {
		movingRight = true;
	}
	

	void Update () {
		if (movingRight) {
			transform.position = Vector2.MoveTowards (transform.position, right.position, moveSpeed * Time.deltaTime);
		} else {
			transform.position = Vector2.MoveTowards (transform.position, left.position, moveSpeed * Time.deltaTime);
		}
	}

	private void OnTriggerEnter2D(Collider2D other){
		if(other.CompareTag("LiftTrigger")){
			SwitchDirection();
	}
			
		if(other.CompareTag("Player")){
				other.transform.parent = transform;
		}
	}

	private void OnTriggerExit2D(Collider2D other){
		if(other.CompareTag("Player")){
			other.transform.parent = null;
		}
	}
			
	
	private void SwitchDirection(){
		movingRight = !movingRight;
	}
}
