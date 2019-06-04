package utilities


import static groovy.io.FileType.DIRECTORIES

class DeleteNewDirectories {

    def root = "C:\\Users\\s0041664\\Documents\\Development\\Groovy Development\\ParseTranslationClazz"

    ArrayList<File> deleteList = []

    static main(args) {
        new DeleteNewDirectories()
    }

    DeleteNewDirectories() {
        start()
    }

    def start() {
        def rootDir = new File(root)
        rootDir.traverse(type: DIRECTORIES, nameFilter: ~/new/) {
            deleteList << it
        }
        deleteDirectories()
    }

    def deleteDirectories() {
        deleteList.each { println it }
        deleteList.each { it.deleteDir() }
    }
}
