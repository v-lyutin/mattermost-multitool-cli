# Mattermost Multitool CLI

**Mattermost Multitool CLI** is a powerful and flexible command-line application designed to simplify the administration and management of Mattermost workspaces.
This tool provides a convenient and efficient interface for performing a wide range of administrative tasks, helping teams and administrators automate routine actions and streamline daily operations within [Mattermost](https://mattermost.com/).

With Mattermost Multitool CLI, you can easily interact with your Mattermost instance, execute various user and channel management actions, and ensure smoother collaboration across your organization — all from the comfort of your terminal.

---

## How to Run

1. Clone the repository (if you haven't already):
    ```bash
    git clone https://github.com/v-lyutin/mattermost-multitool-cli.git
    cd mattermost-multitool-cli
    ```
2. Build the project (if not already built):
   ```bash
   ./mvnw clean package
   ```
3. Run the CLI application using the terminal:
   ```bash
   java -jar target/mattermost-multitool-cli.jar
   ```
4. After starting the CLI, you need to configure your Mattermost connection. Follow the prompts to enter the following information:
   - *Base URL of your Mattermost server (e.g., https://yourhost.com);*
   - *Team ID (your Mattermost team's unique identifier);*
   - *Access Token (a personal access token for API authentication).*
   
That's it! The CLI menu will appear, and you can start managing your Mattermost workspace directly from your terminal.

---

## Features

- Set up and manage your Mattermost environment directly from the CLI;
- Retrieve channel or direct messages for specific time ranges;
- Send out messages to users, including those without Multi-Factor Authentication (MFA);
- Deactivate user accounts;
- Invite users to a team by email.


### File Inputs

Some functions require you to provide input data using files, such as a list of user emails or message templates.
Please note:
- *All input files must be in plain text format (.txt);*
- *Each entry (email address, channel ID, etc.) should be placed on a new line (line-by-line format).*

Example — `emails.txt`:
```
user1@email.com
user2@email.com
user3@email.com
...
```

### Message Templates with Markdown Support

When sending messages via the CLI, you can write your message templates using Markdown syntax.
This allows you to add formatting, lists, links, and other rich content supported by Mattermost.

Example — `message_template.txt`:
```markdown
**Attention!**

Your account will be updated soon.

- Please make sure your information is correct.
- [Contact support](https://yourcompany.com/support) if you have any questions.

_Thank you for your attention!_
```

Mattermost will render your message with the Markdown formatting, making notifications more clear and engaging for users.

### Built-in Rate Limiting

Mattermost Multitool CLI includes a built-in rate limiter to ensure safe and reliable interactions with the Mattermost API.
This mechanism automatically controls the frequency of requests to avoid exceeding the server's API limits and to prevent potential errors such as "Too Many Requests" (HTTP 429).

If you perform bulk operations (for example, deactivating many user accounts or sending a large number of messages), the CLI will automatically pause and wait when the rate limit is reached, resuming once it's safe to continue.
You don't need to configure anything manually — all throttling is handled under the hood for your convenience.







