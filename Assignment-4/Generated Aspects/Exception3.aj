
import java.io.*;
import java.util.*;
import javamoprt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

class Exception3Monitor_Set extends javamoprt.MOPSet {
	protected Exception3Monitor[] elementData;

	public Exception3Monitor_Set(){
		this.size = 0;
		this.elementData = new Exception3Monitor[4];
	}

	public final int size(){
		while(size > 0 && elementData[size-1].MOP_terminated) {
			elementData[--size] = null;
		}
		return size;
	}

	public final boolean add(MOPMonitor e){
		ensureCapacity();
		elementData[size++] = (Exception3Monitor)e;
		return true;
	}

	public final void endObject(int idnum){
		int numAlive = 0;
		for(int i = 0; i < size; i++){
			Exception3Monitor monitor = elementData[i];
			if(!monitor.MOP_terminated){
				monitor.endObject(idnum);
			}
			if(!monitor.MOP_terminated){
				elementData[numAlive++] = monitor;
			}
		}
		for(int i = numAlive; i < size; i++){
			elementData[i] = null;
		}
		size = numAlive;
	}

	public final boolean alive(){
		for(int i = 0; i < size; i++){
			MOPMonitor monitor = elementData[i];
			if(!monitor.MOP_terminated){
				return true;
			}
		}
		return false;
	}

	public final void endObjectAndClean(int idnum){
		int size = this.size;
		this.size = 0;
		for(int i = size - 1; i >= 0; i--){
			MOPMonitor monitor = elementData[i];
			if(monitor != null && !monitor.MOP_terminated){
				monitor.endObject(idnum);
			}
			elementData[i] = null;
		}
		elementData = null;
	}

	public final void ensureCapacity() {
		int oldCapacity = elementData.length;
		if (size + 1 > oldCapacity) {
			cleanup();
		}
		if (size + 1 > oldCapacity) {
			Exception3Monitor[] oldData = elementData;
			int newCapacity = (oldCapacity * 3) / 2 + 1;
			if (newCapacity < size + 1){
				newCapacity = size + 1;
			}
			elementData = Arrays.copyOf(oldData, newCapacity);
		}
	}

	public final void cleanup() {
		int numAlive = 0 ;
		for(int i = 0; i < size; i++){
			Exception3Monitor monitor = (Exception3Monitor)elementData[i];
			if(!monitor.MOP_terminated){
				elementData[numAlive] = monitor;
				numAlive++;
			}
		}
		for(int i = numAlive; i < size; i++){
			elementData[i] = null;
		}
		size = numAlive;
	}

	public final void event_start(Transaction t) {
		int numAlive = 0 ;
		for(int i = 0; i < this.size; i++){
			Exception3Monitor monitor = (Exception3Monitor)this.elementData[i];
			if(!monitor.MOP_terminated){
				elementData[numAlive] = monitor;
				numAlive++;

				monitor.Prop_1_event_start(t);
				if(monitor.Prop_1_Category_match) {
					monitor.Prop_1_handler_match(t);
				}
			}
		}
		for(int i = numAlive; i < this.size; i++){
			this.elementData[i] = null;
		}
		size = numAlive;
	}

	public final void event_modify(Transaction t) {
		int numAlive = 0 ;
		for(int i = 0; i < this.size; i++){
			Exception3Monitor monitor = (Exception3Monitor)this.elementData[i];
			if(!monitor.MOP_terminated){
				elementData[numAlive] = monitor;
				numAlive++;

				monitor.Prop_1_event_modify(t);
				if(monitor.Prop_1_Category_match) {
					monitor.Prop_1_handler_match(t);
				}
			}
		}
		for(int i = numAlive; i < this.size; i++){
			this.elementData[i] = null;
		}
		size = numAlive;
	}

	public final void event_commit(Transaction t) {
		int numAlive = 0 ;
		for(int i = 0; i < this.size; i++){
			Exception3Monitor monitor = (Exception3Monitor)this.elementData[i];
			if(!monitor.MOP_terminated){
				elementData[numAlive] = monitor;
				numAlive++;

				monitor.Prop_1_event_commit(t);
				if(monitor.Prop_1_Category_match) {
					monitor.Prop_1_handler_match(t);
				}
			}
		}
		for(int i = numAlive; i < this.size; i++){
			this.elementData[i] = null;
		}
		size = numAlive;
	}

	public final void event_rollback(Transaction t) {
		int numAlive = 0 ;
		for(int i = 0; i < this.size; i++){
			Exception3Monitor monitor = (Exception3Monitor)this.elementData[i];
			if(!monitor.MOP_terminated){
				elementData[numAlive] = monitor;
				numAlive++;

				monitor.Prop_1_event_rollback(t);
				if(monitor.Prop_1_Category_match) {
					monitor.Prop_1_handler_match(t);
				}
			}
		}
		for(int i = numAlive; i < this.size; i++){
			this.elementData[i] = null;
		}
		size = numAlive;
	}
}

class Exception3Monitor extends javamoprt.MOPMonitor implements Cloneable, javamoprt.MOPObject {
	public Object clone() {
		try {
			Exception3Monitor ret = (Exception3Monitor) super.clone();
			return ret;
		}
		catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}

	int Prop_1_state;
	static final int Prop_1_transition_start[] = {4, 5, 5, 2, 1, 5};;
	static final int Prop_1_transition_modify[] = {5, 5, 2, 5, 2, 5};;
	static final int Prop_1_transition_commit[] = {5, 5, 0, 5, 0, 5};;
	static final int Prop_1_transition_rollback[] = {5, 5, 3, 5, 3, 5};;

	boolean Prop_1_Category_match = false;

	public Exception3Monitor () {
		Prop_1_state = 0;

	}

	public final void Prop_1_event_start(Transaction t) {
		MOP_lastevent = 0;

		Prop_1_state = Prop_1_transition_start[Prop_1_state];
		Prop_1_Category_match = Prop_1_state == 1;
	}

	public final void Prop_1_event_modify(Transaction t) {
		MOP_lastevent = 1;

		Prop_1_state = Prop_1_transition_modify[Prop_1_state];
		Prop_1_Category_match = Prop_1_state == 1;
	}

	public final void Prop_1_event_commit(Transaction t) {
		MOP_lastevent = 2;

		Prop_1_state = Prop_1_transition_commit[Prop_1_state];
		Prop_1_Category_match = Prop_1_state == 1;
	}

	public final void Prop_1_event_rollback(Transaction t) {
		MOP_lastevent = 3;

		Prop_1_state = Prop_1_transition_rollback[Prop_1_state];
		Prop_1_Category_match = Prop_1_state == 1;
	}

	public final void Prop_1_handler_match (Transaction t){
		{
			System.err.println("Start followed by immediate start is an error.");
			this.reset();
		}

	}

	public final void reset() {
		MOP_lastevent = -1;
		Prop_1_state = 0;
		Prop_1_Category_match = false;
	}

	public javamoprt.ref.MOPWeakReference MOPRef_t;

	//alive_parameters_0 = [Transaction t]
	public boolean alive_parameters_0 = true;

	public final void endObject(int idnum){
		switch(idnum){
			case 0:
			alive_parameters_0 = false;
			break;
		}
		switch(MOP_lastevent) {
			case -1:
			return;
			case 0:
			//start
			//alive_t
			if(!(alive_parameters_0)){
				MOP_terminated = true;
				return;
			}
			break;

			case 1:
			//modify
			//alive_t
			if(!(alive_parameters_0)){
				MOP_terminated = true;
				return;
			}
			break;

			case 2:
			//commit
			//alive_t
			if(!(alive_parameters_0)){
				MOP_terminated = true;
				return;
			}
			break;

			case 3:
			//rollback
			//alive_t
			if(!(alive_parameters_0)){
				MOP_terminated = true;
				return;
			}
			break;

		}
		return;
	}

}

public aspect ExceptionMonitorAspect implements javamoprt.MOPObject {
	javamoprt.map.MOPMapManager ExceptionMapManager;
	public ExceptionMonitorAspect(){
		ExceptionMapManager = new javamoprt.map.MOPMapManager();
		ExceptionMapManager.start();
	}

	// Declarations for the Lock
	static Object Exception_MOPLock = new Object();

	static boolean Exception3_activated = false;

	// Declarations for Indexing Trees
	static javamoprt.map.MOPBasicRefMapOfMonitor Exception3_t_Map = new javamoprt.map.MOPBasicRefMapOfMonitor(0);
	static javamoprt.ref.MOPWeakReference Exception3_t_Map_cachekey_0 = javamoprt.map.MOPBasicRefMapOfMonitor.NULRef;
	static Exception3Monitor Exception3_t_Map_cachenode = null;

	// Trees for References
	static javamoprt.map.MOPRefMap Exception_Transaction_RefMap = Exception3_t_Map;

	pointcut MOP_CommonPointCut() : !within(javamoprt.MOPObject+) && !adviceexecution();
	pointcut Exception3_start(Transaction t) : (call(* Transaction.start()) && target(t)) && MOP_CommonPointCut();
	before (Transaction t) : Exception3_start(t) {
		Exception3_activated = true;
		synchronized(Exception_MOPLock) {
			Exception3Monitor mainMonitor = null;
			javamoprt.map.MOPMap mainMap = null;
			javamoprt.ref.MOPWeakReference TempRef_t;

			// Cache Retrieval
			if (t == Exception3_t_Map_cachekey_0.get()) {
				TempRef_t = Exception3_t_Map_cachekey_0;

				mainMonitor = Exception3_t_Map_cachenode;
			} else {
				TempRef_t = Exception3_t_Map.getRef(t);
			}

			if (mainMonitor == null) {
				mainMap = Exception3_t_Map;
				mainMonitor = (Exception3Monitor)mainMap.getNode(TempRef_t);

				if (mainMonitor == null) {
					mainMonitor = new Exception3Monitor();

					mainMonitor.MOPRef_t = TempRef_t;

					Exception3_t_Map.putNode(TempRef_t, mainMonitor);
				}

				Exception3_t_Map_cachekey_0 = TempRef_t;
				Exception3_t_Map_cachenode = mainMonitor;
			}

			mainMonitor.Prop_1_event_start(t);
			if(mainMonitor.Prop_1_Category_match) {
				mainMonitor.Prop_1_handler_match(t);
			}
		}
	}

	pointcut Exception3_modify(Transaction t) : (call(* Transaction.next()) && target(t)) && MOP_CommonPointCut();
	before (Transaction t) : Exception3_modify(t) {
		Exception3_activated = true;
		synchronized(Exception_MOPLock) {
			Exception3Monitor mainMonitor = null;
			javamoprt.map.MOPMap mainMap = null;
			javamoprt.ref.MOPWeakReference TempRef_t;

			// Cache Retrieval
			if (t == Exception3_t_Map_cachekey_0.get()) {
				TempRef_t = Exception3_t_Map_cachekey_0;

				mainMonitor = Exception3_t_Map_cachenode;
			} else {
				TempRef_t = Exception3_t_Map.getRef(t);
			}

			if (mainMonitor == null) {
				mainMap = Exception3_t_Map;
				mainMonitor = (Exception3Monitor)mainMap.getNode(TempRef_t);

				if (mainMonitor == null) {
					mainMonitor = new Exception3Monitor();

					mainMonitor.MOPRef_t = TempRef_t;

					Exception3_t_Map.putNode(TempRef_t, mainMonitor);
				}

				Exception3_t_Map_cachekey_0 = TempRef_t;
				Exception3_t_Map_cachenode = mainMonitor;
			}

			mainMonitor.Prop_1_event_modify(t);
			if(mainMonitor.Prop_1_Category_match) {
				mainMonitor.Prop_1_handler_match(t);
			}
		}
	}

	pointcut Exception3_commit(Transaction t) : (call(* Transaction.commit()) && target(t)) && MOP_CommonPointCut();
	before (Transaction t) : Exception3_commit(t) {
		Exception3_activated = true;
		synchronized(Exception_MOPLock) {
			Exception3Monitor mainMonitor = null;
			javamoprt.map.MOPMap mainMap = null;
			javamoprt.ref.MOPWeakReference TempRef_t;

			// Cache Retrieval
			if (t == Exception3_t_Map_cachekey_0.get()) {
				TempRef_t = Exception3_t_Map_cachekey_0;

				mainMonitor = Exception3_t_Map_cachenode;
			} else {
				TempRef_t = Exception3_t_Map.getRef(t);
			}

			if (mainMonitor == null) {
				mainMap = Exception3_t_Map;
				mainMonitor = (Exception3Monitor)mainMap.getNode(TempRef_t);

				if (mainMonitor == null) {
					mainMonitor = new Exception3Monitor();

					mainMonitor.MOPRef_t = TempRef_t;

					Exception3_t_Map.putNode(TempRef_t, mainMonitor);
				}

				Exception3_t_Map_cachekey_0 = TempRef_t;
				Exception3_t_Map_cachenode = mainMonitor;
			}

			mainMonitor.Prop_1_event_commit(t);
			if(mainMonitor.Prop_1_Category_match) {
				mainMonitor.Prop_1_handler_match(t);
			}
		}
	}

	pointcut Exception3_rollback(Transaction t) : (call(* Transaction.rollback()) && target(t)) && MOP_CommonPointCut();
	before (Transaction t) : Exception3_rollback(t) {
		Exception3_activated = true;
		synchronized(Exception_MOPLock) {
			Exception3Monitor mainMonitor = null;
			javamoprt.map.MOPMap mainMap = null;
			javamoprt.ref.MOPWeakReference TempRef_t;

			// Cache Retrieval
			if (t == Exception3_t_Map_cachekey_0.get()) {
				TempRef_t = Exception3_t_Map_cachekey_0;

				mainMonitor = Exception3_t_Map_cachenode;
			} else {
				TempRef_t = Exception3_t_Map.getRef(t);
			}

			if (mainMonitor == null) {
				mainMap = Exception3_t_Map;
				mainMonitor = (Exception3Monitor)mainMap.getNode(TempRef_t);

				if (mainMonitor == null) {
					mainMonitor = new Exception3Monitor();

					mainMonitor.MOPRef_t = TempRef_t;

					Exception3_t_Map.putNode(TempRef_t, mainMonitor);
				}

				Exception3_t_Map_cachekey_0 = TempRef_t;
				Exception3_t_Map_cachenode = mainMonitor;
			}

			mainMonitor.Prop_1_event_rollback(t);
			if(mainMonitor.Prop_1_Category_match) {
				mainMonitor.Prop_1_handler_match(t);
			}
		}
	}

}
