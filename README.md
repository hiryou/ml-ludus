# MLPractice
Review CS445/545


## Setup GPU system worklog - 09/2020:
This was the last compatible version-set of things:
- Ubuntu 18.04
- NVidida driver version 390
- Cuda version 9.1
- PyTorch ver which was compiled with Cuda 9.1

On Ubuntu 18.04 system:

Prerequisites: Install Python3 & pip3. Make sure `python` and `pip` are referring to the same python3 version

Compatibility ref: [NVidia version & Cuda](https://docs.nvidia.com/deploy/cuda-compatibility/index.html)

1. Full cleanup old cuda versions [[source](https://stackoverflow.com/questions/56431461/how-to-remove-cuda-completely-from-ubuntu/56827564)]
    ```shell script
    $ sudo apt-get purge nvidia*
    $ sudo apt-get autoremove
    $ sudo apt-get autoclean
    $ sudo rm -rf /usr/local/cuda*
    ```
    Reboot

2. Install NVidia driver ver 390
    
    It seemed 390 was the latest available driver for 1080 GTX cards. And 390 is only compatible w/ Cuda 9.1 [[source](https://docs.nvidia.com/deploy/cuda-compatibility/index.html)], 
    hence the choice of Cuda 9.1 at later step 
    
    Option 1. Install from cmdline [[source](https://gist.github.com/DaneGardner/accd6fd330348543167719002a661bd5)]
    ```shell script
    $ sudo modprobe -r nouveau
    $ sudo apt install nvidia-driver-390 nvidia-headless-390 nvidia-utils-390
    $ sudo modprobe -i nvidia    
    ```
   
    Option 2. Instal through Software & Updates GUI [[instruction](https://medium.com/@IsaacJK/setting-up-a-ubuntu-18-04-1-lts-system-for-deep-learning-and-scientific-computing-fab19f7ca39d)]
    
    Now check that you can communicate with the cards
    ```shell script
    $ nvidia-smi
    Fri Sep 25 14:25:27 2020       
    +-----------------------------------------------------------------------------+
    | NVIDIA-SMI 390.138                Driver Version: 390.138                   |
    |-------------------------------+----------------------+----------------------+
    | GPU  Name        Persistence-M| Bus-Id        Disp.A | Volatile Uncorr. ECC |
    | Fan  Temp  Perf  Pwr:Usage/Cap|         Memory-Usage | GPU-Util  Compute M. |
    |===============================+======================+======================|
    |   0  GeForce GTX 1080    Off  | 00000000:01:00.0  On |                  N/A |
    |  0%   38C    P8    10W / 200W |    732MiB /  8118MiB |      0%      Default |
    +-------------------------------+----------------------+----------------------+
    |   1  GeForce GTX 1080    Off  | 00000000:02:00.0 Off |                  N/A |
    |  0%   34C    P8     8W / 200W |      2MiB /  8119MiB |      0%      Default |
    +-------------------------------+----------------------+----------------------+
                                                                               
    +-----------------------------------------------------------------------------+
    | Processes:                                                       GPU Memory |
    |  GPU       PID   Type   Process name                             Usage      |
    |=============================================================================|
    |    0      1455      G   /usr/lib/xorg/Xorg                            40MiB |
    |    0      1515      G   /usr/bin/gnome-shell                          49MiB |
    |    0     22999      G   /usr/lib/xorg/Xorg                           305MiB |
    |    0     23161      G   /usr/bin/gnome-shell                         215MiB |
    |    0     23791      G   ...AAAAAAAAAAAACAAAAAAAAAA= --shared-files   117MiB |
    +-----------------------------------------------------------------------------+
    ```

3. Install Cuda 9.1 Toolkit [[source](https://gist.github.com/DaneGardner/accd6fd330348543167719002a661bd5)]

    ```shell script
    $ curl -LO https://developer.nvidia.com/compute/cuda/9.1/Prod/local_installers/cuda_9.1.85_387.26_linux
    $ curl -LO https://developer.nvidia.com/compute/cuda/9.1/Prod/patches/1/cuda_9.1.85.1_linux
    $ curl -LO https://developer.nvidia.com/compute/cuda/9.1/Prod/patches/2/cuda_9.1.85.2_linux
    $ curl -LO https://developer.nvidia.com/compute/cuda/9.1/Prod/patches/3/cuda_9.1.85.3_linux
    
    # install main toolkit without driver or samples (driver already installed above)
    $ sudo sh cuda_9.1.85_387.26_linux --silent --override --toolkit
    
    # install the patches
    $ sudo sh cuda_9.1.85.1_linux --silent --accept-eula
    $ sudo sh cuda_9.1.85.2_linux --silent --accept-eula
    $ sudo sh cuda_9.1.85.3_linux --silent --accept-eula
   
    # set system wide paths
    $ echo 'PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/usr/local/cuda/bin"' | sudo tee /etc/environment
    $ echo /usr/local/cuda-9.1/lib64 | sudo tee /etc/ld.so.conf.d/cuda-9.1.conf
    $ sudo ldconfig
    ```
    Reboot

    Check cuda version
    ```shell script
    $ nvcc --version
    nvcc: NVIDIA (R) Cuda compiler driver
    Copyright (c) 2005-2017 NVIDIA Corporation
    Built on Fri_Nov__3_21:07:56_CDT_2017
    Cuda compilation tools, release 9.1, V9.1.85
   
    $ cat /usr/local/cuda/version.txt 
    CUDA Version 9.1.85
    CUDA Patch Version 9.1.85.1
    CUDA Patch Version 9.1.85.2
    CUDA Patch Version 9.1.85.3
    ```  
   
4. Install PyTorch with Cuda 9.1 [[source](https://varhowto.com/install-pytorch-cuda-9-1/)]
    
    ```shell script
    $ pip install torch==1.1.0 torchvision==0.3.0 -f https://download.pytorch.org/whl/cu90/torch_stable.html
    ```
    Here we install the PyThon binary for CUDA 9.0, because PyTorch does not officially support (i.e., skipped) CUDA 9.1. But it should be compatible
    
## Verify that python can use cuda
```python
>>> import torch
>>> torch.cuda.is_available()
True
>>> torch.cuda.device_count()
2
>>> torch.cuda.current_device()
0
>>> torch.cuda.get_device_properties(0)
_CudaDeviceProperties(name='GeForce GTX 1080', major=6, minor=1, total_memory=8118MB, multi_processor_count=20)
>>> torch.cuda.get_device_properties(1)
_CudaDeviceProperties(name='GeForce GTX 1080', major=6, minor=1, total_memory=8119MB, multi_processor_count=20)
>>> 
```

