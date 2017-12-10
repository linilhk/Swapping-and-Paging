import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Main {
	LinkedList<LinkedList<Integer>> processes = new LinkedList<LinkedList<Integer>>(); //List of linkedlists A-K
	LinkedList<LinkedList<Integer>> processNum = new LinkedList<LinkedList<Integer>>();
	LinkedList<Integer> toProcessQueue = new LinkedList<>();
	LinkedList<String> Result = new LinkedList<>();

	//hit ratio counters
	double FIFOHitCount = 0;
	double RANDOMHitCount = 0;
	double LRUHitCount = 0;
	double MFUHitCount = 0;
	double LFUHitCount =0;

	// Generate processes A through K
	LinkedList<Integer> A = new LinkedList<Integer>();
	LinkedList<Integer> B = new LinkedList<Integer>();
	LinkedList<Integer> C = new LinkedList<Integer>();
	LinkedList<Integer> D = new LinkedList<Integer>();
	LinkedList<Integer> E = new LinkedList<Integer>();
	LinkedList<Integer> F = new LinkedList<Integer>();
	LinkedList<Integer> G = new LinkedList<Integer>();
	LinkedList<Integer> H = new LinkedList<Integer>();
	LinkedList<Integer> I = new LinkedList<Integer>();
	LinkedList<Integer> J = new LinkedList<Integer>();
	LinkedList<Integer> K = new LinkedList<Integer>();

	//Print out all the algorithms
	public static void main(String[] args) {
		int runCount = 1;
        Main test = new Main();
        for(int i = 0; i < 5; i++){
        System.out.println("-----FIRST IN FIRST OUT RUN " + runCount + "-----");
        test.FIFO();
        System.out.println("-----RANDOM PICK  "+ runCount + "-----");
        test.RandomPick();
        System.out.println("-----LEAST RECENTLY USED RUN " + runCount + "-----");
        test.LRU();
        System.out.println("-----MOST FREQUENTLY USED RUN " + runCount + "-----");
        test.MFU();
        System.out.println("-----LEAST FREQUENTLY USED RUN " + runCount + "-----");
        test.LFU();

        runCount++;
        }

        test.hitRatio();
    }

	public void addInfo(LinkedList<Integer> i, int j){
		// Adding reference number at pos 0. Used for conditional statements to check for certain processes.
		i.add(j);
		i.add(pageSize()); //Added to pos 1. Give each process a set page size
		i.add(serviceDuration());	// Added to pos 2. Give each process a set service duration
		processNum.add(i);  // Add processes into a linked list to generate randomly
	}

	public void fillProcess() {
		//Fill processes with their information
		addInfo(A,1);
		addInfo(B,2);
		addInfo(C,3);
		addInfo(D,4);
		addInfo(E,5);
		addInfo(F,6);
		addInfo(G,7);
		addInfo(H,8);
		addInfo(I,9);
		addInfo(J,10);
		addInfo(K,11);

		// Populate process queue 150 of the above processes in random amounts
		// per process.
		for (int i = 0; i < 150; i++) {
			Collections.shuffle(processNum);
			toProcessQueue = processNum.get(0);
			processes.add(toProcessQueue);
		}
	}

	// Generate a random page amount from 5,11,17 and 31
	public static int pageSize() {
		LinkedList<Integer> pageNum = new LinkedList<Integer>();
		pageNum.add(5);
		pageNum.add(11);
		pageNum.add(17);
		pageNum.add(31);

		// Shuffle values and return number at position 0
		Collections.shuffle(pageNum);

		return pageNum.get(0);

	}

	// Generate a random service duration form 1 to 5
	public static int serviceDuration() {
		Random rand = new Random();
		int duration = 0;
		int max = 5;
		int min = 1;

		duration = rand.nextInt((max - min) + min) + min;

		return duration;
	}

	public void localityOfReference(LinkedList<LinkedList<Integer>> n) {
		int current = 0;
		Random rand = new Random();
		int i = 0;
		// When 0 < r < 7, 70% chance to generate new page reference at -1,
					// 1 or 0 from current
		if (rand.nextInt(10) < 7) {
			i = rand.nextInt(3) - 1;
		} else {
			// When 7 < r < 10, 30% chance to generate new page reference from 2 to
			// 9
			i = rand.nextInt(7) + 2;
		}
		n.get(((i + current) % 10 + 10) % 10);
	}

	//Conditional checks for FIFO
	//@param list of processes, processes A-K, Name of process
	public void FIFOCheck(LinkedList<String> List, LinkedList<Integer> proc, String a){
		int mem = 4;
		String hold = "";
		if(!List.contains(a)){ //Check if the same process is present
			if(List.size() == mem){
				hold =List.getFirst();
				List.removeFirst();  //Remove the element that was put in first.
			}
		List.add(a);
		System.out.println("Paging in process "+a+":"+" Page Size:" + proc.get(1) + " Service Duration:"+proc.get(2) + " Removed: " + hold);
		}
		else if(List.contains(a)){
			FIFOHitCount = FIFOHitCount +1;
			System.out.println(a+" is hit");
		}
		processes.removeFirst();
		System.out.println(List);
	}

	//Conditional checks for Random
	//@param list of processes, processes A-K, Name of process
	public void RandomCheck(LinkedList<String> List, LinkedList<Integer> proc, String a){
		int mem = 4;
		String hold = "";
		int randomPos = new Random().nextInt(mem); //Get a random position
		int posHolder = 0; //Save the random position
		if(!List.contains(a)){
			if(List.size() == mem){
				hold =List.get(randomPos);
				List.remove(randomPos);
				posHolder = randomPos;
			}
			List.add(posHolder, a);
			System.out.println("Paging in process "+a+":"+" Page Size:" + proc.get(1) + " Service Duration:"+proc.get(2)+" Removed: "+ hold);
		}
		else if(List.contains(a)){
			RANDOMHitCount = RANDOMHitCount +1;
			System.out.println(a+" is hit");
		}
		processes.removeFirst();
		System.out.println(List);
	}

	//First in first out implementation
	//Check for each type of list A-K and add, remove or hit accordingly
	public void FIFO(){
		LinkedList<String> Result = new LinkedList<>();
		int time = 0;  //Counter
		fillProcess(); //Call the method to fill the process list
		LinkedList<LinkedList<Integer>> FIFO = new LinkedList<LinkedList<Integer>>();
		while(time < 100){
			FIFO.add(processes.getFirst()); //Get the head of the list
			if(processes.getFirst().get(0) == 1){ //1 is the reference number of A
				FIFOCheck(Result, A, "A");}
			else if(processes.getFirst().get(0) == 2){
				FIFOCheck(Result, B, "B");}
			else if(processes.getFirst().get(0) == 3){
				FIFOCheck(Result, C, "C");}
			else if(processes.getFirst().get(0) == 4){
				FIFOCheck(Result, D, "D");}
			else if(processes.getFirst().get(0) == 5){
				FIFOCheck(Result, E, "E");	}
			else if(processes.getFirst().get(0) == 6){
				FIFOCheck(Result, F, "F");}
			else if(processes.getFirst().get(0) == 7){
				FIFOCheck(Result, G, "G");	}
			else if(processes.getFirst().get(0) == 8){
				FIFOCheck(Result, H, "H");}
			else if(processes.getFirst().get(0) == 9){
				FIFOCheck(Result, I, "I");	}
			else if(processes.getFirst().get(0) == 10){
				FIFOCheck(Result, J, "J");}
			else{
				FIFOCheck(Result, K, "K");	}
			time++;
		}

	}

	//Similar to FIFO, but the position of the evicted process is random
	public void RandomPick(){
		LinkedList<String> Result = new LinkedList<>();
		int time = 0;//Counter
		fillProcess(); //Call the method to fill the process list
		LinkedList<LinkedList<Integer>> RandomPick = new LinkedList<LinkedList<Integer>>();
		while(time < 100){
			RandomPick.add(processes.getFirst());
			if(processes.getFirst().get(0) == 1){ //1 is the reference number of A
				RandomCheck(Result, A, "A");}
			else if(processes.getFirst().get(0) == 2){
				RandomCheck(Result, B, "B");}
			else if(processes.getFirst().get(0) == 3){
				RandomCheck(Result, C, "C");}
			else if(processes.getFirst().get(0) == 4){
				RandomCheck(Result, D, "D");}
			else if(processes.getFirst().get(0) == 5){
				RandomCheck(Result, E, "E");	}
			else if(processes.getFirst().get(0) == 6){
				RandomCheck(Result, F, "F");}
			else if(processes.getFirst().get(0) == 7){
				RandomCheck(Result, G, "G");	}
			else if(processes.getFirst().get(0) == 8){
				RandomCheck(Result, H, "H");}
			else if(processes.getFirst().get(0) == 9){
				RandomCheck(Result, I, "I");	}
			else if(processes.getFirst().get(0) == 10){
				RandomCheck(Result, J, "J");}
			else{
				RandomCheck(Result, K, "K");	}
			time++;
		}

	}

	public void LRU(){
		char[] Result = new char[4];
		int time = 0;  //Counter
		fillProcess(); //Call the method to fill the process list
		LinkedList<LinkedList<Integer>> LRU = new LinkedList<LinkedList<Integer>>();
		int count[] = new int[4];
		for (int i = 0; i < 4; i++)
		{
			count[i] = 0;
		}
		while(time < 100){
			LRU.add(processes.getFirst()); //Get the head of the list
			int LRUpos = 0;
			int least = count[0];
			for (int i = 1; i < 4; i++)
			{
				if (count[i]< least)
				{
					least = count[i];
					LRUpos = i;
				}
			}
			if(processes.getFirst().get(0) == 1){ //1 is the reference number of A
				boolean contains = false;
				for (int i = 0; i < 4; i++)
				{
					if (Result[i] == 'A')
						contains = true;
				}
				if(!contains){
					Result[LRUpos] = 'A';
					count[LRUpos] = count[LRUpos] + 1;
					System.out.println("Paging in process A:"+" Page Size:" + A.get(1) + " Service Duration:"+A.get(2));
				}
				else{
					LRUHitCount++;
					System.out.println("A is hit");
				}
				processes.removeFirst();
				System.out.print("[");
				for (int i = 0; i < 4; i++)
				{
					if(i != 3)
						System.out.print(Result[i] + ", ");
					else
						System.out.print(Result[i]);
				}
				System.out.println("]");
			}
			else if(processes.getFirst().get(0) == 2){
				boolean contains = false;
				for (int i = 0; i < 4; i++)
				{
					if (Result[i] == 'B')
						contains = true;
				}
				if(!contains){
					Result[LRUpos] = 'B';
					count[LRUpos] = count[LRUpos] + 1;
					System.out.println("Paging in process B:"+" Page Size:" + B.get(1) + " Service Duration:"+B.get(2));
				}
				else{
					LRUHitCount++;
					System.out.println("B is hit");
				}
				processes.removeFirst();
				System.out.print("[");
				for (int i = 0; i < 4; i++)
				{
					if(i != 3)
						System.out.print(Result[i] + ", ");
					else
						System.out.print(Result[i]);
				}
				System.out.println("]");
			}
			else if(processes.getFirst().get(0) == 3){
				boolean contains = false;
				for (int i = 0; i < 4; i++)
				{
					if (Result[i] == 'C')
						contains = true;
				}
				if(!contains){
					Result[LRUpos] = 'C';
					count[LRUpos] = count[LRUpos] + 1;
					System.out.println("Paging in process C:"+" Page Size:" + C.get(1) + " Service Duration:"+C.get(2));
				}
				else{
					LRUHitCount++;
					System.out.println("C is hit");
				}
				processes.removeFirst();
				System.out.print("[");
				for (int i = 0; i < 4; i++)
				{
					if(i != 3)
						System.out.print(Result[i] + ", ");
					else
						System.out.print(Result[i]);
				}
				System.out.println("]");
			}
			else if(processes.getFirst().get(0) == 4){
				boolean contains = false;
				for (int i = 0; i < 4; i++)
				{
					if (Result[i] == 'D')
						contains = true;
				}
				if(!contains){
					Result[LRUpos] = 'D';
					count[LRUpos] = count[LRUpos] + 1;
					System.out.println("Paging in process D:"+" Page Size:" + D.get(1) + " Service Duration:"+D.get(2));
				}
				else{
					LRUHitCount++;
					System.out.println("D is hit");
				}
				processes.removeFirst();
				System.out.print("[");
				for (int i = 0; i < 4; i++)
				{
					if(i != 3)
						System.out.print(Result[i] + ", ");
					else
						System.out.print(Result[i]);
				}
				System.out.println("]");
			}
			else if(processes.getFirst().get(0) == 5){
				boolean contains = false;
				for (int i = 0; i < 4; i++)
				{
					if (Result[i] == 'E')
						contains = true;
				}
				if(!contains){
					Result[LRUpos] = 'E';
					count[LRUpos] = count[LRUpos] + 1;
					System.out.println("Paging in process E:"+" Page Size:" + E.get(1) + " Service Duration:"+E.get(2));
				}
				else{
					LRUHitCount++;
					System.out.println("E is hit");
				}
				processes.removeFirst();
				System.out.print("[");
				for (int i = 0; i < 4; i++)
				{
					if(i != 3)
						System.out.print(Result[i] + ", ");
					else
						System.out.print(Result[i]);
				}
				System.out.println("]");
			}
			else if(processes.getFirst().get(0) == 6){
				boolean contains = false;
				for (int i = 0; i < 4; i++)
				{
					if (Result[i] == 'F')
						contains = true;
				}
				if(!contains){
					Result[LRUpos] = 'F';
					count[LRUpos] = count[LRUpos] + 1;
					System.out.println("Paging in process F:"+" Page Size:" + F.get(1) + " Service Duration:"+F.get(2));
				}
				else{
					LRUHitCount++;
					System.out.println("F is hit");
				}
				processes.removeFirst();
				System.out.print("[");
				for (int i = 0; i < 4; i++)
				{
					if(i != 3)
						System.out.print(Result[i] + ", ");
					else
						System.out.print(Result[i]);
				}
				System.out.println("]");
			}
			else if(processes.getFirst().get(0) == 7){
				boolean contains = false;
				for (int i = 0; i < 4; i++)
				{
					if (Result[i] == 'G')
						contains = true;
				}
				if(!contains){
					Result[LRUpos] = 'G';
					count[LRUpos] = count[LRUpos] + 1;
					System.out.println("Paging in process G:"+" Page Size:" + G.get(1) + " Service Duration:"+G.get(2));
				}
				else{
					LRUHitCount++;
					System.out.println("G is hit");
				}
				processes.removeFirst();
				System.out.print("[");
				for (int i = 0; i < 4; i++)
				{
					if(i != 3)
						System.out.print(Result[i] + ", ");
					else
						System.out.print(Result[i]);
				}
				System.out.println("]");
			}
			else if(processes.getFirst().get(0) == 8){
				boolean contains = false;
				for (int i = 0; i < 4; i++)
				{
					if (Result[i] == 'H')
						contains = true;
				}
				if(!contains){
					Result[LRUpos] = 'H';
					count[LRUpos] = count[LRUpos] + 1;
					System.out.println("Paging in process H:"+" Page Size:" + H.get(1) + " Service Duration:"+H.get(2));
				}
				else{
					LRUHitCount++;
					System.out.println("H is hit");
				}
				processes.removeFirst();
				System.out.print("[");
				for (int i = 0; i < 4; i++)
				{
					if(i != 3)
						System.out.print(Result[i] + ", ");
					else
						System.out.print(Result[i]);
				}
				System.out.println("]");
			}
			else if(processes.getFirst().get(0) == 9){
				boolean contains = false;
				for (int i = 0; i < 4; i++)
				{
					if (Result[i] == 'I')
						contains = true;
				}
				if(!contains){
					Result[LRUpos] = 'I';
					count[LRUpos] = count[LRUpos] + 1;
					System.out.println("Paging in process I:"+" Page Size:" + I.get(1) + " Service Duration:"+I.get(2));
				}
				else{
					LRUHitCount++;
					System.out.println("I is hit");
				}
				processes.removeFirst();
				System.out.print("[");
				for (int i = 0; i < 4; i++)
				{
					if(i != 3)
						System.out.print(Result[i] + ", ");
					else
						System.out.print(Result[i]);
				}
				System.out.println("]");
			}
			else if(processes.getFirst().get(0) == 10){
				boolean contains = false;
				for (int i = 0; i < 4; i++)
				{
					if (Result[i] == 'J')
						contains = true;
				}
				if(!contains){
					Result[LRUpos] = 'J';
					count[LRUpos] = count[LRUpos] + 1;
					System.out.println("Paging in process J:"+" Page Size:" + J.get(1) + " Service Duration:"+J.get(2));
				}
				else{
					LRUHitCount++;
					System.out.println("J is hit");
				}
				processes.removeFirst();
				System.out.print("[");
				for (int i = 0; i < 4; i++)
				{
					if(i != 3)
						System.out.print(Result[i] + ", ");
					else
						System.out.print(Result[i]);
				}
				System.out.println("]");
			}
			else{
				boolean contains = false;
				for (int i = 0; i < 4; i++)
				{
					if (Result[i] == 'K')
						contains = true;
				}
				if(!contains){
					Result[LRUpos] = 'K';
					count[LRUpos] = count[LRUpos] + 1;
					System.out.println("Paging in process K:"+" Page Size:" + K.get(1) + " Service Duration:"+K.get(2));
				}
				else{
					LRUHitCount++;
					System.out.println("K is hit");
				}
				processes.removeFirst();
				System.out.print("[");
				for (int i = 0; i < 4; i++)
				{
					if(i != 3)
						System.out.print(Result[i] + ", ");
					else
						System.out.print(Result[i]);
				}
				System.out.println("]");
			}
			time++;
		}

	}


	public void hitRatio(){
		System.out.println("-----HIT RATIOS-----");
		System.out.println("FIFO:" + FIFOHitCount/5);
		System.out.println("Random:"+ RANDOMHitCount/5);
		System.out.println("LRU:"+ LRUHitCount/5);
		System.out.println("MFU:" + MFUHitCount/5);
		System.out.println("LFU:" + LFUHitCount/5);
	}


	public int findMin(int[] ds) {
		int idx = -1;
		double d = Double.POSITIVE_INFINITY;
		for (int i = 0; i < ds.length; i++)
			if (ds[i] < d) {
				d = ds[i];
				idx = i;
			}
		return idx;
	}
	public int findMax(int[] array ) {
		int index = 0;
		int largest = Integer.MIN_VALUE;
		for ( int i = 0; i < array.length; i++ )
		{
		    if ( array[i] > largest )
		    {
		        largest = array[i];
		        index = i;
		    }
		}
		return index;

	}




	// Every page that gets added to the buffer gets a counter. The counter will
	// increment when the same page is added to the buffer
	// When the buffer reaches max capacity, the pages
	// with the lowest frequency get removed from the buffer to make space for the
	// new
	// page.
	// If all pages have the same counter, FIFO will be used
	public void LFU() {
		int time = 0; // Counter
		fillProcess(); // Call the method to fill the process list
		// int[] timeA = new int[4];
		LinkedList<LinkedList<Integer>> LFUlist = new LinkedList<LinkedList<Integer>>();
		// LinkedList<Integer> freq = new LinkedList<Integer>();

		int pos = 0;
		int indexA = 0;
		int indexB = 0;
		int indexC = 0;
		int indexD = 0;
		int indexE = 0;
		int indexF = 0;
		int indexG = 0;
		int indexH = 0;
		int indexI = 0;
		int indexJ = 0;
		int indexK = 0;
		int indexW = 0;
		int mem = 4;
		int temp = 0;
		int[] check = new int[4];
		Tuple tupe1 = new Tuple(0, 0);
		Tuple tupe2 = new Tuple(0, 0);
		Tuple tupe3 = new Tuple(0, 0);
		Tuple tupe4 = new Tuple(0, 0);
		Tuple[] freq = { tupe1, tupe2, tupe3, tupe4 };

		while (time < 100) {
			LFUlist.add(processes.getFirst()); // Get the head of the list
			if (processes.getFirst().get(0) == 1) { // 1 is the reference number
													// of A
				if (!Result.contains("A")) {
					if (Result.size() == mem) {
						System.out.println("Page eviction for page: " + Result.get(findMin(check)));
						Result.remove(findMin(check));
						//System.out.println("Removed at index: " + findMin(check));
						//indexA = 0;
					}

					Result.add("A");
					indexA++;
					temp = Result.indexOf("A");
					check[temp] = indexA;
					System.out.println(
							"Paging in process A:" + " Page Size:" + A.get(1) + " Service Duration:" + A.get(2));
					// //System.out.println("Letter" + freq[temp].x);
					// //System.out.println("Freq" + freq[temp].y);
				}

				else if (Result.contains("A")) {
					temp = Result.indexOf("A")-1;
					System.out.println("A is hit");
					LFUHitCount++;
					indexA++;
					tupe1.x = "A";
					tupe1.y = indexA;
					//System.out.println("Letter" + freq[temp].x);
					//System.out.println("Freq" + freq[temp].y);
				}
				temp = Result.indexOf("A");
				freq[temp] = tupe1;
				tupe1.x = "A";
				tupe1.y = indexA;
				check[temp] = indexA;
				//System.out.println("Frequency Counter" + Arrays.toString(check));
				processes.removeFirst();
				System.out.println(Result);
			} else if (processes.getFirst().get(0) == 2) {
				if (!Result.contains("B")) {
					if (Result.size() == mem) {
						System.out.println("Page eviction for page: " + Result.get(findMin(check)));
						Result.remove(findMin(check));
						//System.out.println("Removed at index " + findMin(check));
						//indexB = 0;
					}
					Result.add("B");
					indexB++;
					temp = Result.indexOf("B");
					check[temp] = indexB;

					System.out.println(
							"Paging in process B:" + " Page Size:" + B.get(1) + " Service Duration:" + B.get(2));
					// //System.out.println("Letter" + freq[temp].x);
					// //System.out.println("Freq" + freq[temp].y);
				}

				else if (Result.contains("B")) {
					temp = Result.indexOf("B")-1;
					System.out.println("B is hit");
					LFUHitCount++;
					indexB++;
					tupe1.x = "B";
					tupe1.y = indexB;
					////System.out.println("Letter" + freq[temp].x);
					////System.out.println("Freq" + freq[temp].y);
				}
				tupe1.x = "B";
				tupe1.y = indexB;
				temp = Result.indexOf("B");
				freq[temp] = tupe1;
				check[temp] = indexB;
				//System.out.println("Frequency Counter" + Arrays.toString(check));
				processes.removeFirst();
				System.out.println(Result);
			} else if (processes.getFirst().get(0) == 3) { // 1 is the reference
															// number of A
				if (!Result.contains("C")) {
					if (Result.size() == mem) {
						System.out.println("Page eviction for page: " + Result.get(findMin(check)));
						Result.remove(findMin(check));
						//System.out.println("Removed at index: " + findMin(check));
					}

					Result.add("C");
					indexC++;
					temp = Result.indexOf("C");
					check[temp] = indexC;

					System.out.println(
							"Paging in process C:" + " Page Size:" + C.get(1) + " Service Duration:" + C.get(2));
					// //System.out.println("Letter" + freq[temp].x);
					// //System.out.println("Freq" + freq[temp].y);
				}

				else if (Result.contains("C")) {
					temp = Result.indexOf("C")-1;
					System.out.println("C is hit");
					LFUHitCount++;
					indexC++;
					tupe1.x = "C";
					tupe1.y = indexC;
					//System.out.println("Letter" + freq[temp].x);
					//System.out.println("Freq" + freq[temp].y);
				}
				temp = Result.indexOf("C");
				freq[temp] = tupe1;
				tupe1.x = "C";
				tupe1.y = indexC;
				check[temp] = indexC;
				//System.out.println("Frequency Counter" + Arrays.toString(check));
				processes.removeFirst();
				System.out.println(Result);
			} else if (processes.getFirst().get(0) == 4) {
				if (!Result.contains("D")) {
					if (Result.size() == mem) {
						temp = Result.indexOf("D");
						System.out.println("Page eviction for page: " + Result.get(findMin(check)));
						Result.remove(findMin(check));
						//System.out.println("Removed at index: " + findMin(check));



						// Result.remove(findMin(check));
						//indexD = 0;
					}

					Result.add("D");
					indexD++;
					temp = Result.indexOf("D");
					check[temp] = indexD;

					System.out.println(
							"Paging in process D:" + " Page Size:" + D.get(1) + " Service Duration:" + D.get(2));
					// //System.out.println("Letter" + freq[temp].x);
					/// //System.out.println("Freq" + freq[temp].y);
				}

				else if (Result.contains("D")) {
					temp = Result.indexOf("D")-1;
					System.out.println("D is hit");
					LFUHitCount++;
					indexD++;
					tupe1.x = "D";
					tupe1.y = indexD;
					//System.out.println("Letter" + freq[temp].x);
					//System.out.println("Freq" + freq[temp].y);
				}
				temp = Result.indexOf("D");
				freq[temp] = tupe1;
				tupe1.x = "D";
				tupe1.y = indexD;
				check[temp] = indexD;
				//System.out.println("Frequency Counter" + Arrays.toString(check));
				processes.removeFirst();
				System.out.println(Result);
			}

			else if (processes.getFirst().get(0) == 5) {
				if (!Result.contains("E")) {
					if (Result.size() == mem) {

						System.out.println("Page eviction for page: " + Result.get(findMin(check)));
						Result.remove(findMin(check));
						//System.out.println("Removed at index: " + findMin(check));


						// Result.remove(findMin(check));
						//indexE = 0;
					}

					Result.add("E");
					indexE++;
					temp = Result.indexOf("E");
					check[temp] = indexE;

					System.out.println(
							"Paging in process E:" + " Page Size:" + E.get(1) + " Service Duration:" + E.get(2));
					// //System.out.println("Letter" + freq[temp].x);
					// //System.out.println("Freq" + freq[temp].y);
				}

				else if (Result.contains("E")) {
					temp = Result.indexOf("E")-1;
					System.out.println("E is hit");
					LFUHitCount++;
					indexE++;
					tupe1.x = "E";
					tupe1.y = indexE;
					//System.out.println("Letter" + freq[temp].x);
					//System.out.println("Freq" + freq[temp].y);
				}
				temp = Result.indexOf("E");
				freq[temp] = tupe1;
				tupe1.x = "E";
				tupe1.y = indexE;
				check[temp] = indexE;
				//System.out.println("Frequency Counter" + Arrays.toString(check));
				processes.removeFirst();
				System.out.println(Result);
			} else if (processes.getFirst().get(0) == 6) {
				if (!Result.contains("F")) {
					if (Result.size() == mem) {

						System.out.println("Page eviction for page: " + Result.get(findMin(check)));
						Result.remove(findMin(check));
						//System.out.println("Removed at index: " + findMin(check));
						//temp = Result.indexOf("F");

						// Result.remove(findMin(check));
						//indexF = 0;
					}

					Result.add("F");
					indexF++;
					temp = Result.indexOf("F");
					check[temp] = indexF;

					System.out.println(
							"Paging in process F:" + " Page Size:" + F.get(1) + " Service Duration:" + F.get(2));
					// //System.out.println("Letter" + freq[temp].x);
					// //System.out.println("Freq" + freq[temp].y);
				}

				else if (Result.contains("F")) {
					temp = Result.indexOf("F")-1;
					System.out.println("F is hit");
					LFUHitCount++;

					indexF++;
					tupe1.x = "F";
					tupe1.y = indexF;
					//System.out.println("Letter" + freq[temp].x);
					//System.out.println("Freq" + freq[temp].y);
				}
				temp = Result.indexOf("F");
				tupe1.x = "F";
				tupe1.y = indexF;
				freq[temp] = tupe1;
				check[temp] = indexF;
				//System.out.println("Frequency Counter" + Arrays.toString(check));
				processes.removeFirst();
				System.out.println(Result);
			} else if (processes.getFirst().get(0) == 7) {
				if (!Result.contains("G")) {
					if (Result.size() == mem) {
						System.out.println("Page eviction for page: " + Result.get(findMin(check)));
						Result.remove(findMin(check));
						//System.out.println("Removed at index: " + findMin(check));
						//temp = Result.indexOf("G");

						// Result.remove(findMin(check));
						//indexG = 0;
					}

					Result.add("G");
					indexG++;
					temp = Result.indexOf("G");
					check[temp] = indexG;

					System.out.println(
							"Paging in process G:" + " Page Size:" + G.get(1) + " Service Duration:" + G.get(2));
					// System.out.println("Letter " + freq[temp].x);
					// //System.out.println("Freq" + freq[temp].y);
				}

				else if (Result.contains("G")) {
					temp = Result.indexOf("G")-1;
					System.out.println("G is hit");
					LFUHitCount++;
					indexG++;
					tupe1.x = "G";
					tupe1.y = indexG;
					//System.out.println("Letter" + freq[temp].x);
					//System.out.println("Freq" + freq[temp].y);
				}
				temp = Result.indexOf("G");
				tupe1.x = "G";
				tupe1.y = indexG;

				freq[temp] = tupe1;
				check[temp] = indexG;
				//System.out.println("Frequency Counter" + Arrays.toString(check));
				processes.removeFirst();
				System.out.println(Result);
			} else if (processes.getFirst().get(0) == 8) {
				if (!Result.contains("H")) {
					if (Result.size() == mem) {
						System.out.println("Page eviction for page: " + Result.get(findMin(check)));
						Result.remove(findMin(check));
						//System.out.println("Removed at index: " + findMin(check));
						//temp = Result.indexOf("H");

						// Result.remove(findMin(check));
						//indexH = 0;
					}

					Result.add("H");
					indexH++;
					temp = Result.indexOf("H");
					check[temp] = indexH;

					System.out.println(
							"Paging in process H:" + " Page Size:" + H.get(1) + " Service Duration:" + H.get(2));
					 //System.out.println("Letter" + freq[temp].x);
					 //System.out.println("Freq" + freq[temp].y);
				}

				else if (Result.contains("H")) {
					temp = Result.indexOf("H")-1;
					System.out.println("H is hit");
					LFUHitCount++;
					indexH++;
					//check[temp] = indexH;
					tupe1.x = "H";
					tupe1.y = indexH;
					//System.out.println("Letter" + freq[temp].x);
					//System.out.println("Freq" + freq[temp].y);
				}
				temp = Result.indexOf("H");
				tupe1.x = "H";
				tupe1.y = indexH;
				freq[temp] = tupe1;
				check[temp] = indexJ;
				//System.out.println("Frequency Counter" + Arrays.toString(check));
				processes.removeFirst();
				System.out.println(Result);
			} else if (processes.getFirst().get(0) == 9) {
				if (!Result.contains("I")) {
					if (Result.size() == mem) {


						System.out.println("Page eviction for page: " + Result.get(findMin(check)));
						Result.remove(findMin(check));
						//System.out.println("Removed at index: " + findMin(check));
						//temp = Result.indexOf("I");

						// Result.remove(findMin(check));
						//indexI = 0;
					}

					Result.add("I");
					indexI++;
					temp = Result.indexOf("I");
					check[temp] = indexI;

					System.out.println(
							"Paging in process I:" + " Page Size:" + I.get(1) + " Service Duration:" + I.get(2));
					// //System.out.println("Letter" + freq[temp].x);
					// //System.out.println("Freq" + freq[temp].y);
				}

				else if (Result.contains("I")) {
					temp = Result.indexOf("I")-1;
					System.out.println("I is hit");
					LFUHitCount++;
					indexI++;
					tupe1.x = "I";
					tupe1.y = indexI;
					//System.out.println("Letter" + freq[temp].x);
					//System.out.println("Freq" + freq[temp].y);
				}
				temp = Result.indexOf("I");
				tupe1.x = "I";
				tupe1.y = indexI;
				freq[temp] = tupe1;
				check[temp] = indexI;
				//System.out.println("Frequency Counter" + Arrays.toString(check));
				processes.removeFirst();
				System.out.println(Result);
			} else if (processes.getFirst().get(0) == 10) {
				if (!Result.contains("J")) {
					if (Result.size() == mem) {
						System.out.println("Page eviction for page: " + Result.get(findMin(check)));
						Result.remove(findMin(check));
						//System.out.println("Removed at index: " + findMin(check));
						//temp = Result.indexOf("J");

						// Result.remove(findMin(check));
						//indexJ = 0;
					}

					Result.add("J");
					indexJ++;
					temp = Result.indexOf("J");
					check[temp] = indexJ;

					System.out.println(
							"Paging in process J:" + " Page Size:" + J.get(1) + " Service Duration:" + J.get(2));
					// //System.out.println("Letter" + freq[temp].x);
					// //System.out.println("Freq" + freq[temp].y);
				}

				else if (Result.contains("J")) {
					temp = Result.indexOf("J") -1;
					System.out.println("J is hit");
					LFUHitCount++;
					indexJ++;
					tupe1.x = "J";
					tupe1.y = indexJ;
					//System.out.println("Letter" + freq[temp].x);
					//System.out.println("Freq" + freq[temp].y);
				}
				temp = Result.indexOf("J");
				tupe1.x = "J";
				tupe1.y = indexJ;
				freq[temp] = tupe1;
				check[temp] = tupe1.y;
				//System.out.println("Frequency Counter" + Arrays.toString(check));
				processes.removeFirst();
				System.out.println(Result);
			} else if (processes.getFirst().get(0) == 11) {
				if (!Result.contains("K")) {
					if (Result.size() == mem) {
						System.out.println("Page eviction for page: " + Result.get(findMin(check)));
						Result.remove(findMin(check));
						//System.out.println("Removed at index: " + findMin(check));

						//indexK = 0;
					}

					Result.add("K");
					indexK++;
					temp = Result.indexOf("K");
					check[temp] = indexK;
					System.out.println(
							"Paging in process K:" + " Page Size:" + K.get(1) + " Service Duration:" + K.get(2));
				}

				else if (Result.contains("K")) {
					temp = Result.indexOf("K")-1;
					System.out.println("K is hit");
					LFUHitCount++;
					indexK++;
					tupe1.x = "K";
					tupe1.y = indexK;

					 //System.out.println("Letter" + freq[temp].x);
					 //System.out.println("Freq" + freq[temp].y);
				}
				temp = Result.indexOf("K");
				tupe1.x = "K";
				tupe1.y = indexK;
				check[temp] = indexK;
				//System.out.println("Frequency Counter" + Arrays.toString(check));
				processes.removeFirst();
				System.out.println(Result);
			}

			time++;
		}
	}




// Every page that gets added to the buffer gets a counter. The counter will
// increment when the same page is added to the buffer
// When the buffer reaches max capacity, the pages
// with the highest frequency get removed from the buffer to make space for the new page.
// If all pages have the same counter, FIFO will be used
public void MFU() {
	int time = 0; // Counter
	fillProcess(); // Call the method to fill the process list
	LinkedList<LinkedList<Integer>> MFUlist = new LinkedList<LinkedList<Integer>>();

	// int[] timeA = new int[4];

	// LinkedList<Integer> freq = new LinkedList<Integer>();

	int pos = 0;
	int indexA = 0;
	int indexB = 0;
	int indexC = 0;
	int indexD = 0;
	int indexE = 0;
	int indexF = 0;
	int indexG = 0;
	int indexH = 0;
	int indexI = 0;
	int indexJ = 0;
	int indexK = 0;
	int indexW = 0;
	int mem = 4;
	int temp = 0;
	int[] check = new int[4];
	Tuple tupe1 = new Tuple(0, 0);
	Tuple tupe2 = new Tuple(0, 0);
	Tuple tupe3 = new Tuple(0, 0);
	Tuple tupe4 = new Tuple(0, 0);
	Tuple[] freq = { tupe1, tupe2, tupe3, tupe4 };

	while (time < 100) {
		MFUlist.add(processes.getFirst()); // Get the head of the list
		if (processes.getFirst().get(0) == 1) { // 1 is the reference number
												// of A
			if (!Result.contains("A")) {
				if (Result.size() == mem) {
					System.out.println("Page eviction for page: " + Result.get(findMax(check)));
					Result.remove(findMax(check));
					//System.out.println("Removed at index: " + findMax(check));
					//indexA = 0;
				}

				Result.add("A");
				indexA++;
				temp = Result.indexOf("A");
				check[temp] = indexA;
				System.out.println(
						"Paging in process A:" + " Page Size:" + A.get(1) + " Service Duration:" + A.get(2));
				// //System.out.println("Letter" + freq[temp].x);
				// //System.out.println("Freq" + freq[temp].y);
			}

			else if (Result.contains("A")) {
				temp = Result.indexOf("A")-1;
				System.out.println("A is hit");
				MFUHitCount++;
				indexA++;
				tupe1.x = "A";
				tupe1.y = indexA;
				//System.out.println("Letter" + freq[temp].x);
				//System.out.println("Freq" + freq[temp].y);
			}
			temp = Result.indexOf("A");
			freq[temp] = tupe1;
			tupe1.x = "A";
			tupe1.y = indexA;
			check[temp] = indexA;
			//System.out.println("Frequency Counter" + Arrays.toString(check));
			processes.removeFirst();
			System.out.println(Result);
		} else if (processes.getFirst().get(0) == 2) {
			if (!Result.contains("B")) {
				if (Result.size() == mem) {
					System.out.println("Page eviction for page: " + Result.get(findMax(check)));
					Result.remove(findMax(check));
					//System.out.println("Removed at index " + findMax(check));
					//indexB = 0;
				}
				Result.add("B");
				indexB++;
				temp = Result.indexOf("B");
				check[temp] = indexB;

				System.out.println(
						"Paging in process B:" + " Page Size:" + B.get(1) + " Service Duration:" + B.get(2));
				// //System.out.println("Letter" + freq[temp].x);
				// //System.out.println("Freq" + freq[temp].y);
			}

			else if (Result.contains("B")) {
				temp = Result.indexOf("B")-1;
				System.out.println("B is hit");
				MFUHitCount++;
				indexB++;
				tupe1.x = "B";
				tupe1.y = indexB;
				////System.out.println("Letter" + freq[temp].x);
				////System.out.println("Freq" + freq[temp].y);
			}
			tupe1.x = "B";
			tupe1.y = indexB;
			temp = Result.indexOf("B");
			freq[temp] = tupe1;
			check[temp] = indexB;
			//System.out.println("Frequency Counter" + Arrays.toString(check));
			processes.removeFirst();
			System.out.println(Result);
		} else if (processes.getFirst().get(0) == 3) { // 1 is the reference
														// number of A
			if (!Result.contains("C")) {
				if (Result.size() == mem) {
					System.out.println("Page eviction for page: " + Result.get(findMax(check)));
					Result.remove(findMax(check));
					//System.out.println("Removed at index: " + findMax(check));
				}

				Result.add("C");
				indexC++;
				temp = Result.indexOf("C");
				check[temp] = indexC;

				System.out.println(
						"Paging in process C:" + " Page Size:" + C.get(1) + " Service Duration:" + C.get(2));
				// //System.out.println("Letter" + freq[temp].x);
				// //System.out.println("Freq" + freq[temp].y);
			}

			else if (Result.contains("C")) {
				temp = Result.indexOf("C")-1;
				System.out.println("C is hit");
				MFUHitCount++;
				indexC++;
				tupe1.x = "C";
				tupe1.y = indexC;
				//System.out.println("Letter" + freq[temp].x);
				//System.out.println("Freq" + freq[temp].y);
			}
			temp = Result.indexOf("C");
			freq[temp] = tupe1;
			tupe1.x = "C";
			tupe1.y = indexC;
			check[temp] = indexC;
			//System.out.println("Frequency Counter" + Arrays.toString(check));
			processes.removeFirst();
			System.out.println(Result);
		} else if (processes.getFirst().get(0) == 4) {
			if (!Result.contains("D")) {
				if (Result.size() == mem) {
					temp = Result.indexOf("D");
					System.out.println("Page eviction for page: " + Result.get(findMax(check)));
					Result.remove(findMax(check));
					//System.out.println("Removed at index: " + findMax(check));



					// Result.remove(findMax(check));
					//indexD = 0;
				}

				Result.add("D");
				indexD++;
				temp = Result.indexOf("D");
				check[temp] = indexD;

				System.out.println(
						"Paging in process D:" + " Page Size:" + D.get(1) + " Service Duration:" + D.get(2));
				// //System.out.println("Letter" + freq[temp].x);
				/// //System.out.println("Freq" + freq[temp].y);
			}

			else if (Result.contains("D")) {
				temp = Result.indexOf("D")-1;
				System.out.println("D is hit");
				MFUHitCount++;
				indexD++;
				tupe1.x = "D";
				tupe1.y = indexD;
				//System.out.println("Letter" + freq[temp].x);
				//System.out.println("Freq" + freq[temp].y);
			}
			temp = Result.indexOf("D");
			freq[temp] = tupe1;
			tupe1.x = "D";
			tupe1.y = indexD;
			check[temp] = indexD;
			//System.out.println("Frequency Counter" + Arrays.toString(check));
			processes.removeFirst();
			System.out.println(Result);
		}

		else if (processes.getFirst().get(0) == 5) {
			if (!Result.contains("E")) {
				if (Result.size() == mem) {

					System.out.println("Page eviction for page: " + Result.get(findMax(check)));
					Result.remove(findMax(check));
					//System.out.println("Removed at index: " + findMax(check));


					// Result.remove(findMax(check));
					//indexE = 0;
				}

				Result.add("E");
				indexE++;
				temp = Result.indexOf("E");
				check[temp] = indexE;

				System.out.println(
						"Paging in process E:" + " Page Size:" + E.get(1) + " Service Duration:" + E.get(2));
				// //System.out.println("Letter" + freq[temp].x);
				// //System.out.println("Freq" + freq[temp].y);
			}

			else if (Result.contains("E")) {
				temp = Result.indexOf("E")-1;
				System.out.println("E is hit");
				MFUHitCount++;
				indexE++;
				tupe1.x = "E";
				tupe1.y = indexE;
				//System.out.println("Letter" + freq[temp].x);
				//System.out.println("Freq" + freq[temp].y);
			}
			temp = Result.indexOf("E");
			freq[temp] = tupe1;
			tupe1.x = "E";
			tupe1.y = indexE;
			check[temp] = indexE;
			//System.out.println("Frequency Counter" + Arrays.toString(check));
			processes.removeFirst();
			System.out.println(Result);
		} else if (processes.getFirst().get(0) == 6) {
			if (!Result.contains("F")) {
				if (Result.size() == mem) {

					System.out.println("Page eviction for page: " + Result.get(findMax(check)));
					Result.remove(findMax(check));
					//System.out.println("Removed at index: " + findMax(check));
					//temp = Result.indexOf("F");

					// Result.remove(findMax(check));
					//indexF = 0;
				}

				Result.add("F");
				indexF++;
				temp = Result.indexOf("F");
				check[temp] = indexF;

				System.out.println(
						"Paging in process F:" + " Page Size:" + F.get(1) + " Service Duration:" + F.get(2));
				// //System.out.println("Letter" + freq[temp].x);
				// //System.out.println("Freq" + freq[temp].y);
			}

			else if (Result.contains("F")) {
				temp = Result.indexOf("F")-1;
				System.out.println("F is hit");
				MFUHitCount++;

				indexF++;
				tupe1.x = "F";
				tupe1.y = indexF;
				//System.out.println("Letter" + freq[temp].x);
				//System.out.println("Freq" + freq[temp].y);
			}
			temp = Result.indexOf("F");
			tupe1.x = "F";
			tupe1.y = indexF;
			freq[temp] = tupe1;
			check[temp] = indexF;
			//System.out.println("Frequency Counter" + Arrays.toString(check));
			processes.removeFirst();
			System.out.println(Result);
		} else if (processes.getFirst().get(0) == 7) {
			if (!Result.contains("G")) {
				if (Result.size() == mem) {
					System.out.println("Page eviction for page: " + Result.get(findMax(check)));
					Result.remove(findMax(check));
					//System.out.println("Removed at index: " + findMax(check));
					//temp = Result.indexOf("G");

					// Result.remove(findMax(check));
					//indexG = 0;
				}

				Result.add("G");
				indexG++;
				temp = Result.indexOf("G");
				check[temp] = indexG;

				System.out.println(
						"Paging in process G:" + " Page Size:" + G.get(1) + " Service Duration:" + G.get(2));
				// System.out.println("Letter " + freq[temp].x);
				// //System.out.println("Freq" + freq[temp].y);
			}

			else if (Result.contains("G")) {
				temp = Result.indexOf("G")-1;
				System.out.println("G is hit");
				MFUHitCount++;

				indexG++;
				tupe1.x = "G";
				tupe1.y = indexG;
				//System.out.println("Letter" + freq[temp].x);
				//System.out.println("Freq" + freq[temp].y);
			}
			temp = Result.indexOf("G");
			tupe1.x = "G";
			tupe1.y = indexG;

			freq[temp] = tupe1;
			check[temp] = indexG;
			//System.out.println("Frequency Counter" + Arrays.toString(check));
			processes.removeFirst();
			System.out.println(Result);
		} else if (processes.getFirst().get(0) == 8) {
			if (!Result.contains("H")) {
				if (Result.size() == mem) {
					System.out.println("Page eviction for page: " + Result.get(findMax(check)));
					Result.remove(findMax(check));
					//System.out.println("Removed at index: " + findMax(check));
					//temp = Result.indexOf("H");

					// Result.remove(findMax(check));
					//indexH = 0;
				}

				Result.add("H");
				indexH++;
				temp = Result.indexOf("H");
				check[temp] = indexH;

				System.out.println(
						"Paging in process H:" + " Page Size:" + H.get(1) + " Service Duration:" + H.get(2));
				 //System.out.println("Letter" + freq[temp].x);
				 //System.out.println("Freq" + freq[temp].y);
			}

			else if (Result.contains("H")) {
				temp = Result.indexOf("H")-1;
				System.out.println("H is hit");
				MFUHitCount++;

				indexH++;
				//check[temp] = indexH;
				tupe1.x = "H";
				tupe1.y = indexH;
				//System.out.println("Letter" + freq[temp].x);
				//System.out.println("Freq" + freq[temp].y);
			}
			temp = Result.indexOf("H");
			tupe1.x = "H";
			tupe1.y = indexH;
			freq[temp] = tupe1;
			check[temp] = indexJ;
			//System.out.println("Frequency Counter" + Arrays.toString(check));
			processes.removeFirst();
			System.out.println(Result);
		} else if (processes.getFirst().get(0) == 9) {
			if (!Result.contains("I")) {
				if (Result.size() == mem) {


					System.out.println("Page eviction for page: " + Result.get(findMax(check)));
					Result.remove(findMax(check));
					//System.out.println("Removed at index: " + findMax(check));
					//temp = Result.indexOf("I");

					// Result.remove(findMax(check));
					//indexI = 0;
				}

				Result.add("I");
				indexI++;
				temp = Result.indexOf("I");
				check[temp] = indexI;

				System.out.println(
						"Paging in process I:" + " Page Size:" + I.get(1) + " Service Duration:" + I.get(2));
				// //System.out.println("Letter" + freq[temp].x);
				// //System.out.println("Freq" + freq[temp].y);
			}

			else if (Result.contains("I")) {
				temp = Result.indexOf("I")-1;

				System.out.println("I is hit");
				MFUHitCount++;
				indexI++;
				tupe1.x = "I";
				tupe1.y = indexI;
				//System.out.println("Letter" + freq[temp].x);
				//System.out.println("Freq" + freq[temp].y);
			}
			temp = Result.indexOf("I");
			tupe1.x = "I";
			tupe1.y = indexI;
			freq[temp] = tupe1;
			check[temp] = indexI;
			//System.out.println("Frequency Counter" + Arrays.toString(check));
			processes.removeFirst();
			System.out.println(Result);
		} else if (processes.getFirst().get(0) == 10) {
			if (!Result.contains("J")) {
				if (Result.size() == mem) {
					System.out.println("Page eviction for page: " + Result.get(findMax(check)));
					Result.remove(findMax(check));
					//System.out.println("Removed at index: " + findMax(check));
					//temp = Result.indexOf("J");

					// Result.remove(findMax(check));
					//indexJ = 0;
				}

				Result.add("J");
				indexJ++;
				temp = Result.indexOf("J");
				check[temp] = indexJ;

				System.out.println(
						"Paging in process J:" + " Page Size:" + J.get(1) + " Service Duration:" + J.get(2));
				// //System.out.println("Letter" + freq[temp].x);
				// //System.out.println("Freq" + freq[temp].y);
			}

			else if (Result.contains("J")) {
				temp = Result.indexOf("J") -1;
				System.out.println("J is hit");
				MFUHitCount++;

				indexJ++;
				tupe1.x = "J";
				tupe1.y = indexJ;
				//System.out.println("Letter" + freq[temp].x);
				//System.out.println("Freq" + freq[temp].y);
			}
			temp = Result.indexOf("J");
			tupe1.x = "J";
			tupe1.y = indexJ;
			freq[temp] = tupe1;
			check[temp] = tupe1.y;
			//System.out.println("Frequency Counter" + Arrays.toString(check));
			processes.removeFirst();
			System.out.println(Result);
		} else if (processes.getFirst().get(0) == 11) {
			if (!Result.contains("K")) {
				if (Result.size() == mem) {
					System.out.println("Page eviction for page: " + Result.get(findMax(check)));
					Result.remove(findMax(check));
					//System.out.println("Removed at index: " + findMax(check));

					//indexK = 0;
				}

				Result.add("K");
				indexK++;
				temp = Result.indexOf("K");
				check[temp] = indexK;
				System.out.println(
						"Paging in process K:" + " Page Size:" + K.get(1) + " Service Duration:" + K.get(2));
			}

			else if (Result.contains("K")) {
				temp = Result.indexOf("K")-1;
				System.out.println("K is hit");
				MFUHitCount++;
				indexK++;
				tupe1.x = "K";
				tupe1.y = indexK;

				 //System.out.println("Letter" + freq[temp].x);
				 //System.out.println("Freq" + freq[temp].y);
			}
			temp = Result.indexOf("K");
			tupe1.x = "K";
			tupe1.y = indexK;
			check[temp] = indexK;
			//System.out.println("Frequency Counter" + Arrays.toString(check));
			processes.removeFirst();
			System.out.println(Result);
		}

		time++;
	}
}

}

class Tuple<X, Y> {
	public X x;
	public int y;

	public Tuple(X x, int y) {
		this.x = x;
		this.y = y;
	}
}
