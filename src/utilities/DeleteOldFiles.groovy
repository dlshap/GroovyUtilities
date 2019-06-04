package utilities


import static groovy.io.FileType.FILES

class DeleteOldFiles {

    Boolean testing = false

    def root = "C:\\Users\\s0041664\\Documents\\Development\\Groovy Development\\test"

    ArrayList<File> deleteList = []

    static main(args) {
        new DeleteOldFiles()
    }

    DeleteOldFiles() {
        start()
    }

    def start() {
        def rootDir = new File(root)
        rootDir.traverse(type: FILES, nameFilter: ~/.*\.groovy_old$/) {
            deleteList << it
        }
        deleteDirectories()
    }

    def pTest() {
        if (testing) {
            println "testing (no real deletions)"
        }
    }

    def deleteDirectories() {
        pTest()
        deleteList.each { println it }
        if (!testing) {
            deleteList.each { it.delete() }
        }
        pTest()
    }
}
