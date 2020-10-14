using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Worm : MonoBehaviour {

	public float speed = 3.0f;
	public float damage = 25.0f;
	public Transform frontCheck;
	public LayerMask layerMask;
    public Transform player;

	private float facingRight = -1f;
    private float dist;

    public AudioClip takeDamage;
    public AudioClip deathSound;
    private AudioSource myAudioSource;

    // Use this for initialization
    void Start () {
        myAudioSource = GetComponent<AudioSource>();
    }
	
	// Update is called once per frame
	void Update () {
        dist = Vector3.Distance(player.position, transform.position);
        if (dist < 3)
        {
            transform.Translate(new Vector3(facingRight, 0f, 0f) * speed * Time.deltaTime);
        }


		if (Physics2D.OverlapPoint (frontCheck.position, layerMask)) {
			facingRight *= -1f;
			transform.localScale = new Vector3 (transform.localScale.y * -facingRight, transform.localScale.y, transform.localScale.z);
		}
	}

	public void Die(){
		Collider2D[] colliders = GetComponents<Collider2D>();
		foreach (var item in colliders){
			item.enabled = false;
		}

        myAudioSource.pitch = Random.Range(0.5f, 1.5f);
        myAudioSource.PlayOneShot(deathSound, 0.5f);

        GetComponent<Rigidbody2D> ().AddForce (new Vector2 (2f, 7f), ForceMode2D.Impulse);
		transform.localScale = new Vector3 (transform.localScale.x, -transform.localScale.y, transform.localScale.z);

		Invoke ("DisableObject", 2.0f);
	}

	private void OnTriggerStay2D(Collider2D other){
		if (other.CompareTag ("Player")) {
		    other.GetComponent<PlayerVariables>().Harm(damage);

            myAudioSource.pitch = Random.Range(0.5f, 1.5f);
            myAudioSource.PlayOneShot(takeDamage, 0.5f);
        }
	}

	private void DisableObject(){
		Destroy (gameObject);
	}


}
