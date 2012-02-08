package ar.com.gm.notij.notifier;

import ar.com.gm.notij.notifier.NotificationObservable.Notification;

/**
 * Implementors if <code>NotificationObserver</code> interface will be informed
 * of changes in NotificationObservable objects.
 * 
 * @author gmatheu
 * 
 */
public interface NotificationObserver {

	/**
	 * Called when the observed object is changed. 
	 * 
	 * @param subject
	 *            {@link NotificationObservable} subject
	 * @param notification
	 *            Notification argument
	 */
	void update(NotificationObservable subject, Notification notification);
}
