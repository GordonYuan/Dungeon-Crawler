package dev.game.enemy;

public class Node {
	private int cost;
	private int[] parent;
	
	public Node (int cost, int[] parent) {
		this.cost = cost;
		this.parent = parent;
	}
}