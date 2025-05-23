import java.util.Scanner;
class ContactDirectory {

	static class Node{
		String name;
		String phoneNumber;
		Node left, right;
		
		Node(String name, String phoneNumber) {
			this.name = name;
			this.phoneNumber = phoneNumber; 
		}
	}
	
	private Node root;
	
	static Scanner yourCommand = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		ContactDirectory directory = new ContactDirectory();
		
        while (true) {
            String command = readCommand();
            String given = yourCommand.nextLine();
            String[] nameNum = given.split(" ", 3);
        
            
            switch (command) {
	        case "ADD":
	        	 if (nameNum.length == 3) {
	        		 directory.add(nameNum[1], nameNum[2]);
	        	 }
                 else {
                	 System.out.println("Write as: ADD name phoneNumber");
                 }
                 break;
	        case "SEARCH":
                if (nameNum.length == 2) {
                	directory.search(nameNum[1]);
                }
                else {
                	System.out.println("Write as: SEARCH name");
                }
                break;
	        case "DELETE":
                if (nameNum.length == 2) {
                	directory.delete(nameNum[1]);
                }
                else {
                	System.out.println("Write as: DELETE name");
                }
                break;
	        case "SHOW":
	            directory.show();
	            break;
	        default:
	            System.out.println("Your command is invalid. Valid commands are: ADD, SEARCH, DELETE, SHOW.");
            }
		
        }

	}

	public void add(String name, String phoneNumber) {
		root = insert(root, name, phoneNumber);		
	}

	private Node insert(Node node, String name, String phoneNumber) {
        if (node == null) {
            node = new Node(name, phoneNumber);
            return node;
        }
        int comparison = name.toLowerCase().compareTo(node.name.toLowerCase());
        if (comparison < 0) {
            node.left = insert(node.left, name, phoneNumber);
        } else if (comparison > 0) {
            node.right = insert(node.right, name, phoneNumber);
        } else {
            node.phoneNumber = phoneNumber;
            System.out.println("Contact updated: " + name);
        }
        return node;
	}

	private void search(String name) {
		Node contact = searchNode(root, name);
        if (contact != null) {
            System.out.println(contact.phoneNumber + " >");
        } else {
            System.out.println("Contact not found.");
        }	
	}
	
	private Node searchNode(Node root, String name) {
	    if (root == null) {
	        return null;
	    }
	    int comparison = name.toLowerCase().compareTo(root.name.toLowerCase());
	    if (comparison == 0) {
	        return root;
	    }
	    if (comparison > 0) {
	        return searchNode(root.right, name);
	    }
	    return searchNode(root.left, name);
	}

	
	private void delete(String name) {
		root = remove(root, name);	
	}

	private Node remove(Node node, String name) {
		if (node == null) {
		    System.out.println("Contact not found.");
			return null;
		}
		
        int comparison = name.toLowerCase().compareTo(node.name.toLowerCase());
        if (comparison < 0) {
            node.left = remove(node.left, name);
        } else if (comparison > 0) {
            node.right = remove(node.right, name);
        } else {
            System.out.println("Contact '" + name + "' deleted.");
            if (node.left == null) 
            	return node.right;
            if (node.right == null) 
            	return node.left;

            Node smallest = findSmallest(node.right);
            node.name = smallest.name;
            node.phoneNumber = smallest.phoneNumber;
            node.right = remove(node.right, smallest.name);
        }
        return node;

	}

	private Node findSmallest(Node node) {
	    return (node.left == null) ? node : findSmallest(node.left);
	}

	private void show() {
		if (root == null) {
			System.out.println("There isn't any contact you have!");
		} else {
			inOrder(root);
		}
	}
	
	private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.name + ": " + node.phoneNumber);
            inOrder(node.right);
        }
		
	}

	private static String readCommand() {
        System.out.print("> ");
        return yourCommand.next().toUpperCase();
	}

}
