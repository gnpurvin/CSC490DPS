
package Connectivity;

/**
 *
 * @author Ray
 */
public interface UserStatusListener {
    public void online(String login);
    public void offline(String login);
}
