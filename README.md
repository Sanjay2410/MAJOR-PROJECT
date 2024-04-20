# MAJOR-PROJECT
# LIGHTWEIGHT CLOUD STORAGE AUDITING WITH DEDUPLICATION AND PRIVACY
<h3>ABSTRACT</h3>
<p>The cloud storage auditing with deduplication is able to verify the integrity of data stored in the cloud while the cloud needs to keep only a single copy of duplicated file. To the best of our knowledge, all of the existing cloud storage auditing schemes with deduplication is vulnerable to brute-force dictionary attacks, which incurs the leakage of user privacy. In this paper, we focus on a new aspect of being against brute-force dictionary attacks on cloud storage auditing. We propose a cloud storage auditing scheme with deduplication supporting strong privacy protection, in which the privacy of user’s file would not be disclosed to the cloud and other parties when this user’s file is predictable or from a small space. In the proposed scheme, we design a novel method to generate the file index for duplicate check, and use a new strategy to generate the key for file encryption. In addition, the user only needs to perform lightweight computation to generate data authenticators, verify cloud data integrity, and retrieve the file from the cloud. The security proof and the performance evaluation demonstrate that the proposed scheme achieves desirable security and efficiency.</p>
<h3>EXISTING SYSTEM</h3>
![image](https://github.com/Sanjay2410/MAJOR-PROJECT/assets/111914998/129a02a4-b7f7-4886-83e8-6b38d31a8432)

<p>Existing cloud storage auditing schemes with deduplication face vulnerabilities due to the use of convergent encryption (CE), which can be compromised by brute-force dictionary attacks, especially for predictable or small-space files. While some schemes introduce a key server to enhance security, they still fall short in preventing attacks, as the key server can be exploited to derive file contents. Additionally, the reliance on file hash values as indices compromises user privacy, as malicious parties can infer file contents through brute-force attacks. Addressing these shortcomings is crucial for developing deduplication mechanisms that offer robust privacy protection.</p>
<h3>PROPOSED SYSTEM</h3>
<p>We introduce a novel approach for generating file indices and employ a new strategy for file encryption keys, ensuring strong privacy protection. Specifically, we utilize an Agency Server (AS) to generate file indices, and users generate encryption keys using a secret file label. This approach safeguards user privacy against both the cloud and AS. Additionally, our system enables efficient data and authenticator deduplication among users who share identical files, enhancing storage efficiency. Moreover, our scheme minimizes user computational burden by requiring lightweight computations for generating data authenticators, verifying cloud data integrity, and retrieving files. Our security analysis demonstrates the correctness, soundness, and strong privacy protection of the proposed scheme, while concrete implementations validate its efficiency.</p>
