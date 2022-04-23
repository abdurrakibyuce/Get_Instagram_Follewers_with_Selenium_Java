public class Main {

    public static void main(String[] args) throws InterruptedException {

        App app = new App();
        app.loginWith("instagramUserName","instagramPassword");
        app.navigateToProfile("zoeisabellakravitz");
        app.clickFollowersCount();
        app.scrollDown();
        app.getFollowerList();

    }
}
