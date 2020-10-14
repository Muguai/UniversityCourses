using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlatformInputs : MonoBehaviour {

	public float speed = 3.0f;
	// public float jumpHeight = 5f;
	public float thrust = 400f;
	public Transform groundcheck;

	private float horizontalDirection;
	private bool grounded;
	private Rigidbody2D rgdb2d;
	private Animator anim;

	// Use this for initialization
	void Start () {
		rgdb2d = GetComponent<Rigidbody2D>();
		anim = GetComponent<Animator>();
		grounded = false;
	}
	
	// Update is called once per frame
	void Update () {
		// Get Player facing direction
		horizontalDirection = Input.GetAxis("Horizontal");

		// Horizonal movement funtionality
		transform.Translate(new Vector3(horizontalDirection, 0, 0) * speed * Time.deltaTime, Camera.main.transform);

		grounded = Physics2D.OverlapPoint(groundcheck.position);

		if (grounded && Input.GetKeyDown(KeyCode.Space)) {
			rgdb2d.AddForce(transform.up * thrust);
			// OLLD JUMP	rgdb2d.velocity += new Vector2 (rgdb2d.velocity.x, jumpHeight);
		}
			

		// Check and change sprite horizontal direction
		if (horizontalDirection > 0) {
			Flip (1);
		} else if (horizontalDirection < 0) {
			Flip (-1);
		}

		anim.SetFloat("Speed", Mathf.Abs(horizontalDirection));
	}

	// Change sprite horizontal direction
	private void Flip(int facingRight) {
		Vector3 myScale = transform.localScale;
		myScale.x = facingRight;
		transform.localScale = myScale;
	}
		
}
